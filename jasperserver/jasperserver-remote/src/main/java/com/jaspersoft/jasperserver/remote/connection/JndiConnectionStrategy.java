/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.remote.connection;

import com.jaspersoft.jasperserver.api.common.error.handling.SecureExceptionHandler;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.BaseJdbcDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.JndiJdbcReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceServiceFactory;
import com.jaspersoft.jasperserver.api.security.validators.Validator;
import com.jaspersoft.jasperserver.dto.connection.datadiscovery.FlatDataSet;
import com.jaspersoft.jasperserver.dto.resources.ClientJndiJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.ConnectionManager;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.Connector;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.JdbcMetadataBuilder;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.resources.converters.JndiJdbcDataSourceResourceConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.ToServerConversionOptions;
import net.sf.jasperreports.engine.JRParameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: JndiConnectionStrategy.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
@Service
public class JndiConnectionStrategy implements ConnectionManagementStrategy<ClientJndiJdbcDataSource>,
        ConnectionMetadataBuilder<ClientJndiJdbcDataSource>,
        ConnectionQueryExecutor<String, ClientJndiJdbcDataSource> {
    protected final Log log = LogFactory.getLog(getClass());
    @Resource(name = "jndiJdbcDataSourceServiceFactory")
    private ReportDataSourceServiceFactory dataSourceFactory;
    @Resource
    private JndiJdbcDataSourceResourceConverter dataSourceResourceConverter;
    @Resource
    private SecureExceptionHandler secureExceptionHandler;
    private ConnectionManager<ClientJndiJdbcDataSource, Connection, String, FlatDataSet, ResourceGroupElement> connectionManager;
    private Map<Connection, BaseJdbcDataSource> openConnections = new HashMap<Connection, BaseJdbcDataSource>();

    @Override
    public ClientJndiJdbcDataSource createConnection(ClientJndiJdbcDataSource connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        boolean passed = false;
        Throwable exception = null;
        JndiJdbcReportDataSource jndiJdbcReportDataSource = dataSourceResourceConverter.
                toServer(connectionDescription, ToServerConversionOptions.getDefault().setSuppressValidation(true));

        try {
            passed = ((BaseJdbcDataSource)dataSourceFactory.createService(jndiJdbcReportDataSource)).testConnection();
        } catch (SQLException vex) {
            if (vex.getMessage().indexOf("[JI_CONNECTION_VALID]") >= 0) passed = true;
            exception = vex;
        } catch(Throwable e) {
            exception = e;
        }
        if(!passed){
            throw new ConnectionFailedException(connectionDescription.getJndiName(), "jndiName", "Invalid JNDI name: "
                    + connectionDescription.getJndiName(), exception, secureExceptionHandler);
        }
        return connectionDescription;
    }

    @Override
    public void deleteConnection(ClientJndiJdbcDataSource connectionDescription, Map<String, Object> data) {
    }

    @Override
    public ClientJndiJdbcDataSource modifyConnection(ClientJndiJdbcDataSource newConnectionDescription, ClientJndiJdbcDataSource oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        // here is nothing to update, just check if it can be connected.
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public ClientJndiJdbcDataSource secureGetConnection(ClientJndiJdbcDataSource connectionDescription, Map<String, Object> data) {
        // no hidden attributes
        return connectionDescription;
    }

    protected BaseJdbcDataSource getJndiDataSourceService(ClientJndiJdbcDataSource connectionDescription){
        JndiJdbcReportDataSource jndiJdbcReportDataSource = dataSourceResourceConverter.toServer(connectionDescription,
                ToServerConversionOptions.getDefault().setSuppressValidation(true));
        return  (BaseJdbcDataSource)dataSourceFactory.createService(jndiJdbcReportDataSource);
    }

    @Override
    public Object build(ClientJndiJdbcDataSource connectionDescription, Map<String, String[]> options, Map<String, Object> contextData) {
        return connectionManager.buildMetadata(connectionDescription, options);
    }

    @PostConstruct
    public void init(){
        connectionManager = new ConnectionManager<ClientJndiJdbcDataSource, Connection, String, FlatDataSet,
                ResourceGroupElement>(new JndiJdbcConnector()).setMetadataBuilder(new JdbcMetadataBuilder());
    }

    @Override
    public Object executeQuery(String query, ClientJndiJdbcDataSource connectionDescriptor, Map<String, Object> data) {
        Validator.validateSQL(query);
        return connectionManager.executeQuery(connectionDescriptor, query);
    }

    @Override
    public Object executeQueryForMetadata(String query, ClientJndiJdbcDataSource connectionDescriptor, Map<String, Object> data) {
        Validator.validateSQL(query);
        return connectionManager.executeQueryForMetadata(connectionDescriptor, query);
    }

    private class JndiJdbcConnector implements Connector<Connection, ClientJndiJdbcDataSource> {

        @Override
        public Connection openConnection(ClientJndiJdbcDataSource connectionDescriptor) {
            final HashMap parameterValues = new HashMap();
            BaseJdbcDataSource jdbcDataSourceService = getJndiDataSourceService(connectionDescriptor);
            jdbcDataSourceService.setReportParameterValues(parameterValues);
            Connection connection = (Connection) parameterValues.get(JRParameter.REPORT_CONNECTION);
            openConnections.put(connection, jdbcDataSourceService);
            return connection;
        }

        @Override
        public void closeConnection(Connection connection) {
            openConnections.remove(connection).closeConnection();
        }
    }

}
