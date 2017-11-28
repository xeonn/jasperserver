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

import com.jaspersoft.jasperserver.api.JSException;
import com.jaspersoft.jasperserver.api.common.crypto.PasswordCipherer;
import com.jaspersoft.jasperserver.api.common.error.handling.SecureExceptionHandler;
import com.jaspersoft.jasperserver.api.common.util.FTPService;
import com.jaspersoft.jasperserver.api.engine.common.util.impl.FTPUtil;
import com.jaspersoft.jasperserver.api.metadata.common.service.JSResourceNotFoundException;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.dto.connection.FtpConnection;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.util.TrustManagerUtils;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: FtpConnectionStrategy.java 62483 2016-04-12 17:26:07Z akasych $
 */
@Service
public class FtpConnectionStrategy implements ConnectionManagementStrategy<FtpConnection> {
    private final static Log log = LogFactory.getLog(FtpConnectionStrategy.class);
    private FTPService ftpService = new FTPUtil();

    @Resource
    private SecureExceptionHandler secureExceptionHandler;
    @Resource(name = "concreteRepository")
    private RepositoryService repository;
    @Resource
    private Map<String, FtpConnectionDescriptionProvider> connectionDescriptionProviders;

    @Override
    public FtpConnection createConnection(FtpConnection connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        FTPService.FTPServiceClient client = null;
        try {

            // Check secure fields for null values and update them if necessary:

            // resolve the original values
            FtpConnection storedFtpConnection = null;
            String holder = connectionDescription.getHolder();
            FtpConnectionDescriptionProvider provider = null;
            if (holder != null) {
                String[] holderParts = holder.split(":");

                if (holderParts.length != 2)
                    throw new IllegalParameterValueException("holder", holder);

                provider = connectionDescriptionProviders.get(holderParts[0]);
                if (provider != null) {
                    storedFtpConnection = provider.getFtpConnectionDescription(holderParts[1]);
                } else {
                    throw new IllegalParameterValueException("holder", holder);
                }
            }
            // restore password
            if (connectionDescription.getPassword() == null) {
                connectionDescription.setPassword(storedFtpConnection != null ? storedFtpConnection.getPassword() : null);
            }
            // restore sshPassphrase
            if (connectionDescription.getSshPassphrase() == null) {
                connectionDescription.setSshPassphrase(storedFtpConnection != null ? storedFtpConnection.getSshPassphrase() : null);
            }


            if (connectionDescription.getType() == FtpConnection.FtpType.ftp) {
                client = ftpService.connectFTP(connectionDescription.getHost(), connectionDescription.getPort(),
                        connectionDescription.getUserName(), connectionDescription.getPassword());
            } else if (connectionDescription.getType() == FtpConnection.FtpType.ftps) {
                client = ftpService.connectFTPS(connectionDescription.getHost(), connectionDescription.getPort(),
                        connectionDescription.getProtocol(), connectionDescription.getImplicit(),
                        connectionDescription.getPbsz(), connectionDescription.getProt(),
                        connectionDescription.getUserName(), connectionDescription.getPassword(), false, TrustManagerUtils.getAcceptAllTrustManager());
            } else if (connectionDescription.getType() == FtpConnection.FtpType.sftp) {

                // Read SSH Private key data from repo file resource
                String sshKeyPath = connectionDescription.getSshKey();
                String sshKeyData = null;
                if (sshKeyPath != null) {
                    try {
                        // get file data
                        sshKeyData = new String(repository.getResourceData(null, sshKeyPath).getData());

                        // decode if encrypted
                        sshKeyData = PasswordCipherer.getInstance().decodePassword(sshKeyData);
                    } catch (JSResourceNotFoundException e) {
                        log.error("Failed to read the SSH Private Key from repository.");
                    } catch (DataAccessResourceFailureException e) {
                        log.warn("Failed to decrypt the SSH Private Key. Most likely reason is unencrypted data in db.");
                        // If not encrypted resources need to be supported then keep the resolved sshKeyData value
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }

                client = ftpService.connectSFTP(connectionDescription.getHost(), connectionDescription.getPort(),
                        connectionDescription.getUserName(), connectionDescription.getPassword(), null,
                        sshKeyData, connectionDescription.getSshPassphrase());
            } else {
                String message = "FtpConnection error: unknown FTP service type: " + connectionDescription.getType();
                log.error(message);
                throw new JSException(message);
            }
            client.changeDirectory(connectionDescription.getFolderPath());
        } catch (UnknownHostException e) {
            throw new ConnectionFailedException(connectionDescription.getHost(), "host", null, e, secureExceptionHandler);
        } catch (Exception e) {
            throw new ConnectionFailedException(connectionDescription, e, secureExceptionHandler);
        } finally {
            if (client != null) try {
                client.disconnect();
            } catch (Exception e) {
                log.error("Couldn't disconnect FTP connection", e);
            }
        }
        return connectionDescription;
    }

    @Override
    public void deleteConnection(FtpConnection connectionDescription, Map<String, Object> data) {
        // do nothing
    }

    @Override
    public FtpConnection modifyConnection(FtpConnection newConnectionDescription, FtpConnection oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        // here is nothing to update, just check if it can be connected.
        return createConnection(newConnectionDescription, data);
    }

    @Override
    public FtpConnection secureGetConnection(FtpConnection connectionDescription, Map<String, Object> data) {
        return new FtpConnection(connectionDescription).setPassword(null);
    }
}
