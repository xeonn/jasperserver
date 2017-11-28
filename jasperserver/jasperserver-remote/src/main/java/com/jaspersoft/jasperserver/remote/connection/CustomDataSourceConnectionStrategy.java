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

import com.jaspersoft.jasperserver.api.engine.common.service.EngineService;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.CustomReportDataSourceServiceFactory;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.CustomDomainMetaDataImpl;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.CustomReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.CustomReportDataSourceService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientProperty;
import com.jaspersoft.jasperserver.remote.customdatasources.TableMetadataConverter;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.resources.converters.CustomDataSourceResourceConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.ToServerConversionOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: CustomDataSourceConnectionStrategy.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
@Service
public class CustomDataSourceConnectionStrategy implements ConnectionManagementStrategy<ClientCustomDataSource>, ConnectionMetadataBuilder<ClientCustomDataSource> {
    @Resource(name = "cdsPropertiesToIgnore")
    private Set<String> propertiesToIgnore;
    @Resource(name = "customDataSourceServiceFactory")
    private CustomReportDataSourceServiceFactory customDataSourceFactory;
    @Resource
    private CustomDataSourceResourceConverter customDataSourceResourceConverter;
    @Resource(name = "concreteRepository")
    private RepositoryService repository;
    @Resource(name = "engineService")
    private EngineService engine;
    @Resource
    private TableMetadataConverter tableMetadataConverter;
    @Override
    public ClientCustomDataSource createConnection(ClientCustomDataSource connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        boolean passed = false;
        Exception exception = null;

        try {
            ReportDataSourceService service = customDataSourceFactory.createService(toServer(connectionDescription));

            passed = !(service instanceof CustomReportDataSourceService) || ((CustomReportDataSourceService) service).testConnection();
        } catch (Exception e) {
            exception = e;
        }
        if (!passed) {
            throw new ConnectionFailedException(connectionDescription, null, "Connection failed", exception);
        }
        return connectionDescription;
    }

    protected CustomReportDataSource toServer(ClientCustomDataSource clientCustomDataSource){
        CustomReportDataSource ds = customDataSourceResourceConverter.
                toServer(clientCustomDataSource, ToServerConversionOptions.getDefault().setSuppressValidation(true));
        // On edit data source we set the null as value for the password if not changed
        // If we get the null from client then set the password from original data source (if it exists)
        if (ds.getPropertyMap() != null && ds.getPropertyMap().get("password") == null && clientCustomDataSource.getUri() != null) {
            CustomReportDataSource existingDs = (CustomReportDataSource) repository.getResource(null, clientCustomDataSource.getUri());
            if (existingDs != null) {
                ds.getPropertyMap().put("password", existingDs.getPropertyMap().get("password"));
            }
        }
        return ds;
    }

    @Override
    public void deleteConnection(ClientCustomDataSource connectionDescription, Map<String, Object> data) {
        // nothing to clean. Do nothing
    }

    @Override
    public ClientCustomDataSource modifyConnection(ClientCustomDataSource newConnectionDescription, ClientCustomDataSource oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public ClientCustomDataSource secureGetConnection(ClientCustomDataSource connectionDescription, Map<String, Object> data) {
        ClientCustomDataSource copy = new ClientCustomDataSource(connectionDescription);
        final List<ClientProperty> properties = copy.getProperties();
        if(properties != null){
            Iterator<ClientProperty> iterator = properties.iterator();
            for(;iterator.hasNext();){
                ClientProperty property = iterator.next();
                if(propertiesToIgnore.contains(property.getKey()))iterator.remove();
            }
        }
        return copy;
    }

    @Override
    public Object build(ClientCustomDataSource connection) {
        try {
            return tableMetadataConverter.toClient((CustomDomainMetaDataImpl)engine.getMetaDataFromConnector(toServer(connection)), null);
        } catch (Exception e) {
            throw new RemoteException(e);
        }
    }
}
