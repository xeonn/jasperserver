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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JdbcQueryExecutor.java 58920 2015-10-30 15:47:09Z ykovalch $
 */
public class JdbcQueryExecutor implements QueryExecutor<String, Connection, List<Map<String, Object>>, Map<String, String>> {
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public DataSet<List<Map<String, Object>>, Map<String, String>> executeQuery(String query, Connection connection) {
        return executeQuery(query, connection, false);
    }

    @Override
    public Map<String, String> executeQueryForMetadata(String query, Connection connection) {
        return executeQuery(query, connection, true).getMetadata();
    }

    protected DataSet<List<Map<String, Object>>, Map<String, String>> executeQuery(String query, Connection connection, boolean skipData) {
        final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        final Map<String, String> columns;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            columns = new HashMap<String, String>();
            final ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                columns.put(metaData.getColumnName(i), metaData.getColumnClassName(i));
            }
            if(!skipData) {
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (String columnName : columns.keySet()) {
                        row.put(columnName, resultSet.getString(columnName));
                    }
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            throw new QueryExecutionException(query, connection.getClass(), e);
        } finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
        }
        return new DataSet<List<Map<String, Object>>, Map<String, String>>().setData(result).setMetadata(columns);
    }
}
