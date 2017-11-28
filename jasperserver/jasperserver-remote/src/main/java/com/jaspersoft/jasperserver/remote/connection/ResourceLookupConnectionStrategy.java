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
package com.jaspersoft.jasperserver.remote.connection;

import com.jaspersoft.jasperserver.api.metadata.common.domain.Resource;
import com.jaspersoft.jasperserver.api.metadata.common.domain.util.ToClientConversionOptions;
import com.jaspersoft.jasperserver.api.metadata.common.domain.util.ToClientConverter;
import com.jaspersoft.jasperserver.api.metadata.common.service.RepositoryService;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributesResolver;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.exception.ReferencedResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.exception.ResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.resources.converters.ResourceConverterProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ResourceLookupConnectionStrategy.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
@Component
public class ResourceLookupConnectionStrategy implements ConnectionManagementStrategy<ClientResourceLookup>,
        ConnectionQueryExecutor<Object, ClientResourceLookup>, GenericTypeMetadataBuilder<ClientResourceLookup> {
    private static final String INNER_UUID = "innerUuid";
    @javax.annotation.Resource(name = "concreteRepository")
    private RepositoryService repository;
    @javax.annotation.Resource
    private ResourceConverterProvider resourceConverterProvider;
    @javax.annotation.Resource
    private ConnectionsManager connectionsManager;
    @javax.annotation.Resource
    private ProfileAttributesResolver profileAttributesResolver;

    protected ClientResource getFullClientResource(ClientResourceLookup resourceLookup){
        ClientResource clientResource = null;
        if(resourceLookup != null && resourceLookup.getUri() != null){
            if(profileAttributesResolver.containsAttribute(resourceLookup.getUri())){
                throw new IllegalParameterValueException("uri", resourceLookup.getUri());
            }
            final Resource repositoryResource = repository
                    .getResource(null, resourceLookup.getUri());
            if(repositoryResource != null){
                final ToClientConverter<? super Resource, ? extends ClientResource, ToClientConversionOptions> toClientConverter =
                        resourceConverterProvider.getToClientConverter(repositoryResource.getResourceType());
                final String clientResourceType = toClientConverter.getClientResourceType();
                if(connectionsManager.getConnectionDescriptionClass(clientResourceType) == null){
                    // this client type isn't supported. Let's fail here
                    throw new RemoteException(new ErrorDescriptor().setMessage("Resource of type [" + clientResourceType +
                            "] is not supported by this endpoint").setParameters(clientResourceType).setErrorCode("not.supported.resource.type"));
                }
                clientResource = toClientConverter
                        .toClient(repositoryResource, ToClientConversionOptions.getDefault());
            } else {
                throw new ReferencedResourceNotFoundException(resourceLookup.getUri(), "uri");
            }
        }
        return clientResource;
    }

    public boolean isMetadataSupported(ClientResourceLookup resourceLookup){
        return connectionsManager.isMetadataSupported(getFullClientResource(resourceLookup));
    }

    protected UUID getInnerUuid(Map<String, Object> data){
        final Object uuid = data.get(INNER_UUID);
        if(uuid instanceof UUID){
            return (UUID) uuid;
        } else {
            throw new ResourceNotFoundException();

        }
    }

    @Override
    public ClientResourceLookup createConnection(ClientResourceLookup connectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        final UUID uuid = connectionsManager.createConnection(getFullClientResource(connectionDescription));
        data.put(INNER_UUID, uuid);
        return connectionDescription;
    }

    @Override
    public void deleteConnection(ClientResourceLookup connectionDescription, Map<String, Object> data) {
        connectionsManager.removeConnection(getInnerUuid(data));
    }

    @Override
    public ClientResourceLookup modifyConnection(ClientResourceLookup newConnectionDescription,
            ClientResourceLookup oldConnectionDescription, Map<String, Object> data) throws IllegalParameterValueException {
        connectionsManager.modifyConnection(getInnerUuid(data), getFullClientResource(newConnectionDescription));
        return newConnectionDescription;
    }

    @Override
    public ClientResourceLookup secureGetConnection(ClientResourceLookup connectionDescription, Map<String, Object> data) {
        // nothing to secure in resource lookup
        return connectionDescription;
    }

    @Override
    public Object build(ClientResourceLookup connection, Map<String, String[]> options, Map<String, Object> contextData) {
        return connectionsManager.getConnectionMetadata(getInnerUuid(contextData), options);
    }

    @Override
    public Object executeQuery(Object query, ClientResourceLookup connection, Map<String, Object> data) {
        return connectionsManager.executeQuery(getInnerUuid(data), query);
    }

    @Override
    public Object executeQueryForMetadata(Object query, ClientResourceLookup connection, Map<String, Object> data) {
        return connectionsManager.executeQueryForMetadata(getInnerUuid(data), query);
    }
}
