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

import com.jaspersoft.jasperserver.dto.resources.domain.SchemaElement;

import java.util.Map;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ConnectionManager.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class ConnectionManager<ConnectionDescriptorType, ConnectionType, QueryType, DataSetType, DataSetMetadataType> {
    private final Connector<ConnectionType, ConnectionDescriptorType> connector;
    private MetadataBuilder<ConnectionType> metadataBuilder;
    private QueryExecutor<QueryType, ConnectionType, DataSetType, DataSetMetadataType> queryExecutor;

    public ConnectionManager(Connector<ConnectionType, ConnectionDescriptorType> connector) {
        this.connector = connector;
    }

    public Connector<ConnectionType, ConnectionDescriptorType> getConnector() {
        return connector;
    }

    public MetadataBuilder<ConnectionType> getMetadataBuilder() {
        return metadataBuilder;
    }

    public ConnectionManager<ConnectionDescriptorType, ConnectionType, QueryType, DataSetType, DataSetMetadataType>
    setMetadataBuilder(MetadataBuilder<ConnectionType> metadataBuilder) {
        this.metadataBuilder = metadataBuilder;
        return this;
    }

    public QueryExecutor<QueryType, ConnectionType, DataSetType, DataSetMetadataType> getQueryExecutor() {
        return queryExecutor;
    }

    public ConnectionManager<ConnectionDescriptorType, ConnectionType, QueryType, DataSetType, DataSetMetadataType>
    setQueryExecutor(QueryExecutor<QueryType, ConnectionType, DataSetType, DataSetMetadataType> queryExecutor) {
        this.queryExecutor = queryExecutor;
        return this;
    }

    public SchemaElement buildMetadata(ConnectionDescriptorType connectionDescriptor, final Map<String, String[]> options) {
        return operateConnection(connectionDescriptor, new ConnectionOperator<SchemaElement, ConnectionType>() {
            @Override
            public SchemaElement operate(ConnectionType connection) {
                return metadataBuilder.build(connection, options);
            }
        });
    }

    public DataSetType executeQuery(ConnectionDescriptorType connectionDescriptor, final QueryType query) {

        return operateConnection(connectionDescriptor, new ConnectionOperator<DataSetType, ConnectionType>() {
            @Override
            public DataSetType operate(ConnectionType connection) {
                return queryExecutor.executeQuery(query, connection);
            }
        });
    }

    public DataSetMetadataType executeQueryForMetadata(ConnectionDescriptorType connectionDescriptor, final QueryType query){
        return operateConnection(connectionDescriptor, new ConnectionOperator<DataSetMetadataType, ConnectionType>() {
            @Override
            public DataSetMetadataType operate(ConnectionType connection) {
                return queryExecutor.executeQueryForMetadata(query, connection);
            }
        });
    }

    protected <R> R operateConnection(ConnectionDescriptorType connectionDescriptor, ConnectionOperator<R, ConnectionType> operator) {
        R result;
        ConnectionType connection = null;
        try {
            connection = connector.openConnection(connectionDescriptor);
            result = operator.operate(connection);
        } finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return result;
    }

    private interface ConnectionOperator<ResultType, ConcreteConnectionType> {
        ResultType operate(ConcreteConnectionType connection);
    }
}
