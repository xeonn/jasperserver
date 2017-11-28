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
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.JdbcReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceServiceFactory;
import com.jaspersoft.jasperserver.api.security.validators.Validator;
import com.jaspersoft.jasperserver.dto.resources.ClientJdbcDataSource;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.ConnectionManager;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.Connector;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.JdbcMetadataBuilder;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.JdbcQueryExecutor;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.resources.converters.JdbcDataSourceResourceConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.ToServerConversionOptions;
import net.sf.jasperreports.engine.JRParameter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: JdbcConnectionStrategy.java 62954 2016-05-01 09:49:23Z ykovalch $
 */
@Service
public class JdbcConnectionStrategy implements ConnectionManagementStrategy<ClientJdbcDataSource>, ConnectionMetadataBuilder<ClientJdbcDataSource>, ConnectionQueryExecutor<String, ClientJdbcDataSource> {
    @Resource
    private MessageSource messageSource;
    @Resource(name = "concreteRepository")
    private RepositoryService repository;
    @Resource(name = "jdbcDataSourceServiceFactory")
    private ReportDataSourceServiceFactory jdbcDataSourceFactory;
    @Resource
    private JdbcDataSourceResourceConverter jdbcDataSourceResourceConverter;
    @Resource
    private JdbcMetadataBuilder jdbcMetadataBuilder;
    private ConnectionManager<ClientJdbcDataSource, Connection, String, List<Map<String, Object>>, Map<String, String>> connectionManager;
    private Map<Connection, BaseJdbcDataSource> openConnections = new HashMap<Connection, BaseJdbcDataSource>();
    @Resource
    private SecureExceptionHandler secureExceptionHandler;

    @Override
    public ClientJdbcDataSource createConnection(ClientJdbcDataSource connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        Exception exception = null;
        boolean passed = false;
        try {
            passed = getJdbcDataSourceService(connectionDescription).testConnection();
        } catch (Exception e) {
            exception = e;
        }
        if (!passed) {
            throw new ConnectionFailedException(connectionDescription, exception, secureExceptionHandler);
        }
        return connectionDescription;
    }

    @Override
    public void deleteConnection(ClientJdbcDataSource connectionDescription, Map<String, Object> data) {
        // nothing to clean. Do nothing.
    }

    @Override
    public ClientJdbcDataSource modifyConnection(ClientJdbcDataSource newConnectionDescription, ClientJdbcDataSource oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public ClientJdbcDataSource secureGetConnection(ClientJdbcDataSource connectionDescription, Map<String, Object> data) {
        return new ClientJdbcDataSource(connectionDescription).setPassword(null);
    }

    protected BaseJdbcDataSource getJdbcDataSourceService(ClientJdbcDataSource connectionDescription){
        JdbcReportDataSource jdbcReportDataSource = jdbcDataSourceResourceConverter.toServer(connectionDescription,
                ToServerConversionOptions.getDefault().setSuppressValidation(true));

        String password = connectionDescription.getPassword();
        final String passwordSubstitution = messageSource.getMessage("input.password.substitution", null, LocaleContextHolder.getLocale());
        if ((password == null || password.equals(passwordSubstitution)) && connectionDescription.getUri() != null) {
            JdbcReportDataSource existingDs = (JdbcReportDataSource) repository.getResource(null, connectionDescription.getUri());
            if (existingDs != null) {
                jdbcReportDataSource.setPassword(existingDs.getPassword());
            }
        }
        return  (BaseJdbcDataSource)jdbcDataSourceFactory.createService(jdbcReportDataSource);
    }

    @Override
    public Object build(ClientJdbcDataSource connectionDescription, Map<String, String[]> options) {
        return connectionManager.buildMetadata(connectionDescription, options);
    }



    @PostConstruct
    public void init(){
        connectionManager = new ConnectionManager<ClientJdbcDataSource, Connection, String, List<Map<String, Object>>, Map<String, String>>(new JdbcConnector())
                .setMetadataBuilder(new JdbcMetadataBuilder()).setQueryExecutor(new JdbcQueryExecutor());
    }

    private class JdbcConnector implements Connector<Connection, ClientJdbcDataSource>{

        @Override
        public Connection openConnection(ClientJdbcDataSource connectionDescriptor) {
            final HashMap parameterValues = new HashMap();
            BaseJdbcDataSource jdbcDataSourceService = getJdbcDataSourceService(connectionDescriptor);
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

    @Override
    public Object executeQuery(String query, ClientJdbcDataSource connectionDescriptor) {
        Validator.validateSQL(query);
        return connectionManager.executeQuery(connectionDescriptor, query);
    }

    @Override
    public Object executeQueryForMetadata(String query, ClientJdbcDataSource connectionDescriptor) {
        Validator.validateSQL(query);
        return connectionManager.executeQueryForMetadata(connectionDescriptor, query);
    }
}
