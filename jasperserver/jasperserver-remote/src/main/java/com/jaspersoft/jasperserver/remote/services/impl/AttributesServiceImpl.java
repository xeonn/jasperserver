/*
* Copyright (C) 2005 - 2009 Jaspersoft Corporation. All rights  reserved.
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
package com.jaspersoft.jasperserver.remote.services.impl;

import com.jaspersoft.jasperserver.api.JSDuplicateResourceException;
import com.jaspersoft.jasperserver.api.metadata.user.domain.ObjectPermission;
import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchCriteria;
import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchResult;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.api.metadata.user.service.TenantService;
import com.jaspersoft.jasperserver.api.metadata.user.service.impl.AttributesSearchResultImpl;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributeEmbeddedContainer;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.exception.ResourceAlreadyExistsException;
import com.jaspersoft.jasperserver.remote.exception.ResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentity;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentityResolver;
import com.jaspersoft.jasperserver.remote.resources.converters.PermissionConverter;
import com.jaspersoft.jasperserver.remote.resources.converters.UserAttributesConverter;
import com.jaspersoft.jasperserver.remote.services.AttributesService;
import com.jaspersoft.jasperserver.remote.services.PermissionsService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Volodya Sabadosh
 * @version $Id: AttributesServiceImpl.java 55311 2015-05-13 16:39:00Z svnaskorodu $
 */
@Component("attributesService")
public class AttributesServiceImpl implements AttributesService {
    @Resource
    protected ProfileAttributeService profileAttributeService;
    @Resource(name="concreteAttributesRecipientIdentityResolver")
    protected RecipientIdentityResolver recipientIdentityResolver;
    @Resource
    private UserAttributesConverter attributesConverter;
    @Resource
    protected PermissionsService attributesPermissionService;
    @Resource(name = "permissionConverter")
    protected PermissionConverter permissionConverter;

    public static String ROOT_HOLDER = "tenant:/";

    /**
     * Deletes  attributes from target attribute <param>holder</param>. If <param>attrNames</param> is specified then
     * remove just attributes which names is included in this set.
     *
     * @param holder
     * @param names attribute to delete names.
     * @throws RemoteException
     */
    public void deleteAttributes(RecipientIdentity holder, Set<String> names) throws RemoteException {
        Object attributeHolder = recipientIdentityResolver.resolveRecipientObject(holder);

        @SuppressWarnings("unchecked")
        AttributesSearchCriteria searchCriteria = new AttributesSearchCriteria.Builder().
                setNames(names).
                setSkipServerSettings(true).build();

        AttributesSearchResult<ProfileAttribute> searchResult = profileAttributeService.
                getProfileAttributesForPrincipal(null, attributeHolder, searchCriteria);

        for (ProfileAttribute attr : searchResult.getList()) {
            try {
                profileAttributeService.deleteProfileAttribute(null, attr);
            } catch (AccessDeniedException ex) {
                throw new com.jaspersoft.jasperserver.remote.exception.AccessDeniedException("Access denied for attribute ",
                        attr.getAttrName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public AttributesSearchResult<ClientUserAttribute>
    getAttributes(AttributesSearchCriteria searchCriteria, boolean includeEffectivePermissionsInResult) throws RemoteException {
        String attributeHolder = searchCriteria.getHolder() == null ||
                searchCriteria.getHolder().equals(ROOT_HOLDER) ? ROOT_HOLDER.concat(TenantService.ORGANIZATIONS) : searchCriteria.getHolder();
        Object recipient;

        try {
            recipient = recipientIdentityResolver.resolveRecipientObject(attributeHolder);
        } catch (Exception ex) {
            return new AttributesSearchResultImpl<ClientUserAttribute>();
        }

        AttributesSearchResult<ProfileAttribute> serverSearchResult = profileAttributeService.
                getProfileAttributesForPrincipal(null, recipient, searchCriteria);

        AttributesSearchResult<ClientUserAttribute> clientAttributes = new AttributesSearchResultImpl<ClientUserAttribute>();
        clientAttributes.setList(new ArrayList<ClientUserAttribute>());

        for (ProfileAttribute serverAttr : serverSearchResult.getList()) {
            ClientUserAttribute clientAttr = attributesConverter.toClient(serverAttr, null);
            if (includeEffectivePermissionsInResult) {
                clientAttr = new HypermediaAttribute(clientAttr);
                HypermediaAttributeEmbeddedContainer embeddedContainer = new HypermediaAttributeEmbeddedContainer();
                embeddedContainer.setRepositoryPermissions(getEffectivePermissions(serverAttr));
                ((HypermediaAttribute)clientAttr).setEmbedded(embeddedContainer);
            }
            clientAttributes.getList().add(clientAttr);
        }
        clientAttributes.setTotalCount(serverSearchResult.getTotalCount());

        return clientAttributes;
    }

    @Override
    public List<ClientUserAttribute> putAttributes(RecipientIdentity holder,
                                                   List<? extends ClientUserAttribute> attributes,
                                                   Set<String> names, boolean includeEffectivePermissionsInResult) throws RemoteException {
        Object attributeHolder = recipientIdentityResolver.resolveRecipientObject(holder);

        List<ProfileAttribute> attributesToDelete = new ArrayList<ProfileAttribute>();
        List<ClientUserAttribute> attributesToPut;

        Set<String> newAttributesNamesSet = new HashSet<String>();

        if (attributes != null) {
            for (ClientUserAttribute attribute : attributes) {
                newAttributesNamesSet.add(attribute.getName());
            }

            attributesToPut = new LinkedList<ClientUserAttribute>(attributes);
        } else {
            attributesToPut = new LinkedList<ClientUserAttribute>();
        }

        AttributesSearchCriteria searchCriteria = new AttributesSearchCriteria.Builder().setNames(names).build();
        List<ProfileAttribute> oldCollection =
                profileAttributeService.getProfileAttributesForPrincipal(null, attributeHolder, searchCriteria).getList();

        if (names == null || names.size() == 0) {
            for (ProfileAttribute pa : oldCollection) {
                if (!newAttributesNamesSet.contains(pa.getAttrName())) {
                    attributesToDelete.add(pa);
                }
            }
        } else {
            Iterator<ClientUserAttribute> iterator = attributesToPut.iterator();
            while (iterator.hasNext()) {
                if (!names.contains(iterator.next().getName())) {
                    iterator.remove();
                }
            }
            for (String attrName : names) {
                if (!newAttributesNamesSet.contains(attrName)) {
                    for (ProfileAttribute attr : oldCollection) {
                        if (attr.getAttrName().equals(attrName)) {
                            attributesToDelete.add(attr);
                            break;
                        }
                    }
                }
            }
        }

        for (ProfileAttribute attr : attributesToDelete) {
            try {
                profileAttributeService.deleteProfileAttribute(null, attr);
            } catch (AccessDeniedException ex) {
                throw new com.jaspersoft.jasperserver.remote.exception.AccessDeniedException("Access denied for attribute ",
                        attr.getAttrName());
            }
        }

        List<ClientUserAttribute> result = new ArrayList<ClientUserAttribute>();
        for (ClientUserAttribute client : attributesToPut) {
            try {
                client.setHolder(recipientIdentityResolver.toRecipientUri(holder));

                ProfileAttribute serverAttr = attributesConverter.toServer(client, null);
                profileAttributeService.putProfileAttribute(null, serverAttr);

                if (client instanceof HypermediaAttribute) {
                    //setup
                    HypermediaAttribute hypermediaAttribute = (HypermediaAttribute) client;
                    if (hypermediaAttribute.getEmbedded() != null &&
                            ((HypermediaAttribute) client).getEmbedded().getRepositoryPermissions() != null) {
                        List<ObjectPermission> objectPermissions = new ArrayList<ObjectPermission>();
                        for (RepositoryPermission repositoryPermission :
                                ((HypermediaAttribute) client).getEmbedded().getRepositoryPermissions()) {
                            repositoryPermission.setUri(serverAttr.getURI());
                            objectPermissions.add(permissionConverter.toServer(repositoryPermission, null));
                        }
                        attributesPermissionService.putPermissions(serverAttr, objectPermissions);
                    }
                }

                ClientUserAttribute updatedClient = attributesConverter.toClient(serverAttr, null);

                if (includeEffectivePermissionsInResult) {
                    HypermediaAttributeEmbeddedContainer embeddedContainer = new HypermediaAttributeEmbeddedContainer();
                    embeddedContainer.setRepositoryPermissions(getEffectivePermissions(serverAttr));
                    HypermediaAttribute hypermediaAttribute = new HypermediaAttribute(updatedClient);
                    hypermediaAttribute.setEmbedded(embeddedContainer);

                    result.add(hypermediaAttribute);
                } else {
                    result.add(updatedClient);
                }
            } catch (AccessDeniedException ex) {
                throw new com.jaspersoft.jasperserver.remote.exception.AccessDeniedException("Access denied for attribute ", client.getName());
            } catch (JSDuplicateResourceException ex) {
                throw new ResourceAlreadyExistsException(new ErrorDescriptor
                        .Builder()
                        .setErrorCode("attribute.duplicate.server.level")
                        .setMessage("The attribute name is reserved for use at the server level")
                        .setParameters(new String[]{client.getName()}).getErrorDescriptor());
            }
        }

        return result;
    }

    @Override
    public List<ClientUserAttribute> getAttributes(RecipientIdentity holder,
                                                   Set<String> names,
                                                   boolean includeEffectivePermissions) throws RemoteException {
        Object recipient = recipientIdentityResolver.resolveRecipientObject(holder);

        AttributesSearchCriteria searchCriteria = new AttributesSearchCriteria.Builder().
                setNames(names).build();

        AttributesSearchResult<ProfileAttribute> serverSearchResult = profileAttributeService.
                getProfileAttributesForPrincipal(null, recipient, searchCriteria);

        List<ClientUserAttribute> clientAttributes = new ArrayList<ClientUserAttribute>();

        for (ProfileAttribute serverAttr : serverSearchResult.getList()) {
            ClientUserAttribute clientAttr = attributesConverter.toClient(serverAttr, null);
            if (includeEffectivePermissions) {
                clientAttr = new HypermediaAttribute(clientAttr);
                HypermediaAttributeEmbeddedContainer embeddedContainer = new HypermediaAttributeEmbeddedContainer();
                embeddedContainer.setRepositoryPermissions(getEffectivePermissions(serverAttr));
                ((HypermediaAttribute)clientAttr).setEmbedded(embeddedContainer);
            }
            clientAttributes.add(clientAttr);
        }

        return clientAttributes;
    }

    protected List<RepositoryPermission> getEffectivePermissions(ProfileAttribute serverObject) {
        List<ObjectPermission> objectPermissions = attributesPermissionService.
                getPermissions(serverObject.getURI(), null, null, true, false);
        List<RepositoryPermission> repositoryPermissions = new ArrayList<RepositoryPermission>();
        for (ObjectPermission objectPermission : objectPermissions) {
            RepositoryPermission repositoryPermission = permissionConverter.toClient(objectPermission, null);
            //skip permission uri.
            repositoryPermission.setUri(null);
            repositoryPermissions.add(repositoryPermission);
        }
        return repositoryPermissions;
    }

}
