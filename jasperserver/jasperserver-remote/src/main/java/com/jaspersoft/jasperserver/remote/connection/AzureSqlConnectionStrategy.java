/*
* Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights  reserved.
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
package com.jaspersoft.jasperserver.remote.connection;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.jaspersoft.jasperserver.api.common.error.handling.SecureExceptionHandler;
import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.BaseJdbcDataSource;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.AzureSqlReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceServiceFactory;
import com.jaspersoft.jasperserver.dto.resources.ClientAzureSqlDataSource;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.resources.converters.AzureSqlDataSourceResourceConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.ToServerConversionOptions;

@Service
public class AzureSqlConnectionStrategy implements ConnectionManagementStrategy<ClientAzureSqlDataSource> {
    @Resource
    private MessageSource messageSource;
    @Resource(name = "concreteRepository")
    private RepositoryService repository;
    @Resource
    private AzureSqlDataSourceResourceConverter azureSqlDataSourceResourceConverter;
    @Resource(name = "azureSqlDataSourceServiceFactory")
    private ReportDataSourceServiceFactory azureSqlDataSourceServiceFactory;
    @Resource
    private SecureExceptionHandler secureExceptionHandler;

    @Override
    public ClientAzureSqlDataSource createConnection(ClientAzureSqlDataSource connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        boolean passed = false;
        Exception exception = null;

        AzureSqlReportDataSource azureSqlReportDataSource = azureSqlDataSourceResourceConverter.toServer(connectionDescription,
                ToServerConversionOptions.getDefault().setSuppressValidation(true));

        try {
            String passwordSubstitution = messageSource.getMessage("input.password.substitution", null,
                    LocaleContextHolder.getLocale());

            // On edit datasource we set the passwordSubstitution to the passwords form fields
            // If we get the substitution from UI then set the password from original datasource (if it exists)
            AzureSqlReportDataSource existingDs = null;
            final String password = connectionDescription.getPassword();
            final String secretKey = connectionDescription.getKeyStorePassword();
            if (password == null || password.equals(passwordSubstitution) || secretKey == null || secretKey.equals(passwordSubstitution)) {
                existingDs = (AzureSqlReportDataSource) repository.getResource(null, connectionDescription.getUri());
            }
            if ((password == null || password.equals(passwordSubstitution)) && existingDs != null) {
                connectionDescription.setPassword(existingDs.getPassword());
                azureSqlReportDataSource.setPassword(existingDs.getPassword());
            }
            if ((secretKey == null || secretKey.equals(passwordSubstitution)) && existingDs != null) {
                connectionDescription.setKeyStorePassword(existingDs.getKeyStorePassword());
                azureSqlReportDataSource.setKeyStorePassword(existingDs.getKeyStorePassword());
            }

            passed = ((BaseJdbcDataSource)azureSqlDataSourceServiceFactory.createService(azureSqlReportDataSource)).testConnection();
        } catch (Exception e) {
            exception = e;
        }
        if (!passed) {
            throw new ConnectionFailedException(connectionDescription, exception, secureExceptionHandler);
        }
        return connectionDescription;
    }

    @Override
    public void deleteConnection(ClientAzureSqlDataSource connectionDescription, Map<String, Object> data) {
        // nothing to clean, do nothing
    }

    @Override
    public ClientAzureSqlDataSource modifyConnection(ClientAzureSqlDataSource newConnectionDescription, ClientAzureSqlDataSource oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public ClientAzureSqlDataSource secureGetConnection(ClientAzureSqlDataSource connectionDescription, Map<String, Object> data) {
        return new ClientAzureSqlDataSource(connectionDescription).setPassword(null).setKeyStorePassword(null);
    }
}