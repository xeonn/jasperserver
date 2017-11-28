/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
* http://www.jaspersoft.com.
*
* Unless you have purchased  a commercial license agreement from Jaspersoft,
* the following license terms  apply:
*
* This program is free software: you can redistribute it and/or  modify
* it under the terms of the GNU Affero General Public License  as
* published by the Free Software Foundation, either version 3 of  the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero  General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public  License
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.remote.connection.datadiscovery;

import com.jaspersoft.jasperserver.api.common.error.handling.SecureExceptionHandler;
import com.jaspersoft.jasperserver.dto.resources.domain.AbstractResourceGroupElement;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceMetadataSingleElement;
import com.jaspersoft.jasperserver.dto.resources.domain.SchemaElement;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JdbcMetadataBuilder.java 61788 2016-03-15 13:35:35Z ykovalch $
 */
@Service
public class JdbcMetadataBuilder implements MetadataBuilder<Connection> {

    @Resource
    private SecureExceptionHandler secureExceptionHandler;

    private static final Map<Integer, String> JDBC_TYPES_BY_CODE = Collections.unmodifiableMap(new HashMap<Integer, String>() {{
        put(Types.BIGINT, "java.lang.Long");
        put(Types.BIT, "java.lang.Boolean");
        put(Types.BOOLEAN, "java.lang.Boolean");
        put(Types.CHAR, "java.lang.String");
        put(Types.DATE, "java.util.Date");
        put(Types.DECIMAL, "java.math.BigDecimal");
        put(Types.DOUBLE, "java.lang.Double");
        put(101, "java.lang.Double");
        put(Types.FLOAT, "java.lang.Float");
        put(100, "java.lang.Float");
        put(Types.INTEGER, "java.lang.Integer");
        put(Types.LONGVARCHAR, "java.lang.String");
        put(Types.NUMERIC, "java.math.BigDecimal");
        put(Types.REAL, "java.math.Double");
        put(Types.SMALLINT, "java.lang.Short");
        put(Types.TIME, "java.sql.Time");
        put(Types.TIMESTAMP, "java.sql.Timestamp");
        put(-101, "java.sql.Timestamp");
        put(-102, "java.sql.Timestamp");
        put(Types.TINYINT, "java.lang.Byte");
        put(Types.VARCHAR, "java.lang.String");
        put(Types.NVARCHAR, "java.lang.String");
    }});

    public SchemaElement build(Connection connection, Map<String, String[]> options) {
        final String[] expands = options != null ? options.get("expand") : null;
        final String[] includes = options != null ? options.get("include") : null;
        List<SchemaElement> items;
        try {
            final DatabaseMetaData metaData = connection.getMetaData();
            if (includes != null && includes.length > 0) {
                items = includeMetadata(includes, metaData);
            } else {
                final Map<String, List<String[]>> expandsMap = new HashMap<String, List<String[]>>();
                if (expands != null) {
                    for (String expand : expands) {
                        final String[] tokens = (expand.startsWith("/") ? expand.substring(1) : expand).split("/");
                        List<String[]> tokensList = expandsMap.get(tokens[0]);
                        if (tokensList == null) {
                            tokensList = new ArrayList<String[]>();
                            expandsMap.put(tokens[0], tokensList);
                        }
                        if (tokens.length > 1) {
                            tokensList.add(Arrays.copyOfRange(tokens, 1, tokens.length));
                        }
                    }
                }
                items = expandMetadata(expandsMap, metaData);
            }
        } catch (SQLException e) {
            throw new RemoteException(e, secureExceptionHandler);
        }
        return new ResourceGroupElement().setElements(items);
    }

    protected List<SchemaElement> expandMetadata(Map<String, List<String[]>> expandsMap, DatabaseMetaData metaData) throws SQLException {
        List<SchemaElement> result = new ArrayList<SchemaElement>();
        final ResultSet schemas = metaData.getSchemas();
        while (schemas.next()) {
            String schema = schemas.getString("TABLE_SCHEM");
            result.add(getSchemaMetadata(schema, expandsMap.get(schema), metaData));
        }
        if(result.isEmpty()){
            // this data source doesn't have any schema. Let's load tables then
            // why expandsMap.keySet() - in this case tables are keys. Single column loading isn't supported
            result = buildTablesMetadata(null, metaData, expandsMap.keySet());
        }
        return result;

    }

    protected List<SchemaElement> includeMetadata(String[] includes, DatabaseMetaData metaData) {
        List<SchemaElement> result = new ArrayList<SchemaElement>();
        for (String include : includes) {
            if (include != null) {
                final String[] path = (include.startsWith("/") ? include.substring(1) : include).split("/");
                switch (path.length) {
                    case 1:
                        result.add(getSchemaMetadata(path[0], new ArrayList<String[]>(), metaData));
                        break;
                    case 2:
                        result.add(getTableMetadata(path[0], path[1], true, metaData));
                        break;
                }
            }
        }
        return result;
    }

    protected SchemaElement getSchemaMetadata(String schema, List<String[]> expand, DatabaseMetaData metaData) {
        final List<SchemaElement> tableItems;
        if (expand != null) {
            final Set<String> tableNamesToExpand = new HashSet<String>();
            for (String[] strings : expand) {
                tableNamesToExpand.add(strings[0]);
            }
            tableItems = buildTablesMetadata(schema, metaData, tableNamesToExpand);
        } else {
            tableItems = null;
        }
        return new ResourceGroupElement().setType("schema").setName(schema).setElements(tableItems);
    }

    protected List<SchemaElement> buildTablesMetadata(String schema, DatabaseMetaData metaData, Set<String> tableNamesToExpand) {
        List<SchemaElement> tableItems = new ArrayList<SchemaElement>();
        try {
            final ResultSet tables = metaData.getTables(null, schema, null, new String[]{"TABLE", "VIEW", "ALIAS", "SYNONYM"});
            while (tables.next()) {
                final String tableName = tables.getString("TABLE_NAME");
                tableItems.add(getTableMetadata(schema, tableName, tableNamesToExpand.contains(tableName), metaData));
            }
        } catch (SQLException e) {
            throw new RemoteException(e, secureExceptionHandler);
        }
        return tableItems;
    }

    protected SchemaElement getTableMetadata(String schema, String table, boolean expand, DatabaseMetaData metaData) {
        AbstractResourceGroupElement result = new ResourceGroupElement().setName(table).setType("table");
        if (expand) {
            final List<SchemaElement> columnsMetadata = new ArrayList<SchemaElement>();
            try {
                final ResultSet columns = metaData.getColumns(null, schema, table, null);
                ResultSet primaryKeySet = metaData.getPrimaryKeys(null, schema, table);
                List<String> primaryKeys = new ArrayList<String>();
                while (primaryKeySet.next()) {
                    primaryKeys.add(primaryKeySet.getString(4));
                }
                final ResultSet foreignKeysSet = metaData.getImportedKeys(null, schema, table);
                Map<String, String> foreignKeyMap = new HashMap<String, String>();
                while (foreignKeysSet.next()) {
                    String foreignKeyColumnName = foreignKeysSet.getString("FKCOLUMN_NAME");
                    String primaryKeyTableName = foreignKeysSet.getString("PKTABLE_NAME");
                    String primaryKeyColumnName = foreignKeysSet.getString("PKCOLUMN_NAME");
                    String primaryKeySchemaName = foreignKeysSet.getString("PKTABLE_SCHEM");
                    foreignKeyMap.put(foreignKeyColumnName, primaryKeySchemaName + "." + primaryKeyTableName + "." + primaryKeyColumnName);
                }
                while (columns.next()) {
                    final String columnName = columns.getString("COLUMN_NAME");
                    int typeCode = columns.getInt("DATA_TYPE");
                    final ResourceMetadataSingleElement columnItem = new ResourceMetadataSingleElement().setName(columnName).setType(JDBC_TYPES_BY_CODE.get(typeCode));
                    if (primaryKeys.contains(columnName)) {
                        columnItem.setIsIdentifier(true);
                    }
                    if (foreignKeyMap.containsKey(columnName)) {
                        columnItem.setReferenceTo(foreignKeyMap.get(columnName));
                    }
                    columnsMetadata.add(columnItem);
                }
            } catch (SQLException e) {
                throw new RemoteException(e, secureExceptionHandler);
            }
            result.setElements(columnsMetadata);
        }
        return result;
    }
}
