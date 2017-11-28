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
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.VirtualReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceServiceFactory;
import com.jaspersoft.jasperserver.dto.connection.datadiscovery.FlatDataSet;
import com.jaspersoft.jasperserver.dto.resources.ClientVirtualDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.ConnectionManager;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.Connector;
import com.jaspersoft.jasperserver.remote.connection.datadiscovery.JdbcMetadataBuilder;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.resources.converters.ToServerConversionOptions;
import com.jaspersoft.jasperserver.remote.resources.converters.VirtualDataSourceResourceConverter;
import net.sf.jasperreports.engine.JRParameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author serhii.blazhyievskyi
 * @version $Id: VirtualConnectionStrategy.java 64791 2016-10-12 15:08:37Z ykovalch $
 */

@Service
public class VirtualConnectionStrategy implements ConnectionManagementStrategy<ClientVirtualDataSource>, ConnectionMetadataBuilder<ClientVirtualDataSource>
{

    protected final Log log = LogFactory.getLog(getClass());
    @Resource(name = "virtualDataSourceServiceFactory")
    private ReportDataSourceServiceFactory dataSourceFactory;
    @Resource
    private SecureExceptionHandler secureExceptionHandler;
    @Resource
    private VirtualDataSourceResourceConverter dataSourceResourceConverter;
    private Map<Connection, BaseJdbcDataSource> openConnections = new HashMap<Connection, BaseJdbcDataSource>();
    private ConnectionManager<ClientVirtualDataSource, Connection, String, FlatDataSet, ResourceGroupElement> connectionManager;

    @Override
    public ClientVirtualDataSource createConnection(ClientVirtualDataSource connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        boolean passed = false;
        Throwable exception = null;

        try {
            passed = getDataSourceService(connectionDescription).testConnection();
        } catch(Throwable e) {
            exception = e;
        }
        if (!passed) {
            throw new ConnectionFailedException(connectionDescription, exception, secureExceptionHandler);
        }
        return connectionDescription;
    }

    @Override
    public void deleteConnection(ClientVirtualDataSource connectionDescription, Map<String, Object> data) {

    }

    @Override
    public ClientVirtualDataSource modifyConnection(ClientVirtualDataSource newConnectionDescription,
                                                    ClientVirtualDataSource oldConnectionDescription,
                                                    Map<String, Object> data) throws IllegalParameterValueException {
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public ClientVirtualDataSource secureGetConnection(ClientVirtualDataSource connectionDescription, Map<String, Object> data) {
        return connectionDescription;
    }

    @PostConstruct
    public void init(){
        connectionManager = new ConnectionManager<ClientVirtualDataSource, Connection, String, FlatDataSet,
                ResourceGroupElement>(new VirtualConnector()).setMetadataBuilder(new JdbcMetadataBuilder());
    }

    @Override
    public Object build(ClientVirtualDataSource connectionDescription, Map<String, String[]> options,
                        Map<String, Object> contextData) {
        return connectionManager.buildMetadata(connectionDescription, options);
    }

    private class VirtualConnector implements Connector<Connection, ClientVirtualDataSource> {

        @Override
        public Connection openConnection(ClientVirtualDataSource connectionDescription) {
            final HashMap parameterValues = new HashMap();
            BaseJdbcDataSource virtualDataSourceService = getDataSourceService(connectionDescription);
            virtualDataSourceService.setReportParameterValues(parameterValues);
            Connection connection = (Connection) parameterValues.get(JRParameter.REPORT_CONNECTION);
            openConnections.put(connection, virtualDataSourceService);
            return connection;
        }

        @Override
        public void closeConnection(Connection connection) {
            openConnections.remove(connection).closeConnection();
        }
    }

    protected BaseJdbcDataSource getDataSourceService(ClientVirtualDataSource connectionDescription){
        VirtualReportDataSource virtualReportDataSource = dataSourceResourceConverter.
                toServer(connectionDescription, ToServerConversionOptions.getDefault().setSuppressValidation(true));
        return  (BaseJdbcDataSource)dataSourceFactory.createService(virtualReportDataSource);
    }

}
