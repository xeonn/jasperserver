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

import com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.BaseJdbcDataSource;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.AwsReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceServiceFactory;
import com.jaspersoft.jasperserver.dto.resources.ClientAwsDataSource;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.resources.converters.AwsDataSourceResourceConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.ToServerConversionOptions;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.Map;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: AwsConnectionStrategy.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
@Service
public class AwsConnectionStrategy implements ConnectionManagementStrategy<ClientAwsDataSource> {
    @Resource
    private MessageSource messageSource;
    @Resource(name = "concreteRepository")
    private RepositoryService repository;
    @Resource
    private AwsDataSourceResourceConverter awsDataSourceResourceConverter;
    @Resource(name = "awsDataSourceServiceFactory")
    private ReportDataSourceServiceFactory awsDataSourceFactory;

    @Override
    public ClientAwsDataSource createConnection(ClientAwsDataSource connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        boolean passed = false;
        Exception exception = null;

        AwsReportDataSource awsReportDataSource = awsDataSourceResourceConverter.toServer(connectionDescription,
                ToServerConversionOptions.getDefault().setSuppressValidation(true));

        try {
            String passwordSubstitution = messageSource.getMessage("input.password.substitution", null,
                    LocaleContextHolder.getLocale());

            // On edit datasource we set the passwordSubstitution to the passwords form fields
            // If we get the substitution from UI then set the password from original datasource (if it exists)
            AwsReportDataSource existingDs = null;
            final String password = connectionDescription.getPassword();
            final String secretKey = connectionDescription.getSecretKey();
            if (password == null || password.equals(passwordSubstitution) || secretKey == null || secretKey.equals(passwordSubstitution)) {
                existingDs = (AwsReportDataSource) repository.getResource(null, connectionDescription.getUri());
            }
            if ((password == null || password.equals(passwordSubstitution)) && existingDs != null) {
                connectionDescription.setPassword(existingDs.getPassword());
                awsReportDataSource.setPassword(existingDs.getPassword());
            }
            if ((secretKey == null || secretKey.equals(passwordSubstitution)) && existingDs != null) {
                connectionDescription.setSecretKey(existingDs.getAWSSecretKey());
                awsReportDataSource.setAWSSecretKey(existingDs.getAWSSecretKey());
            }

            passed = ((BaseJdbcDataSource)awsDataSourceFactory.createService(awsReportDataSource)).testConnection();
        } catch (Exception e) {
            exception = e;
        }
        if (!passed) {
            throw new ConnectionFailedException(connectionDescription, exception);
        }
        return connectionDescription;
    }

    @Override
    public void deleteConnection(ClientAwsDataSource connectionDescription, Map<String, Object> data) {
        // nothing to clean, do nothing
    }

    @Override
    public ClientAwsDataSource modifyConnection(ClientAwsDataSource newConnectionDescription, ClientAwsDataSource oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public ClientAwsDataSource secureGetConnection(ClientAwsDataSource connectionDescription, Map<String, Object> data) {
        return new ClientAwsDataSource(connectionDescription).setPassword(null).setSecretKey(null);
    }
}
