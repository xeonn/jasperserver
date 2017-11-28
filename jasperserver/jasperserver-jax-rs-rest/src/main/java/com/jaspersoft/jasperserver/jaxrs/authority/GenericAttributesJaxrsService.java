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
package com.jaspersoft.jasperserver.jaxrs.authority;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ProfileAttributeImpl;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.common.AttributesConfig;
import com.jaspersoft.jasperserver.jaxrs.common.RestConstants;
import com.jaspersoft.jasperserver.remote.ServiceException;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.exception.ResourceAlreadyExistsException;
import com.jaspersoft.jasperserver.remote.exception.ResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.resources.converters.UserAttributesConverter;
import com.jaspersoft.jasperserver.remote.services.GenericAttributesService;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Volodya Sabadosh
 * @version $Id: GenericAttributesJaxrsService.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
public abstract class GenericAttributesJaxrsService<T> {
    private Pattern empty = Pattern.compile("^\\s*$");
    @Resource
    private AttributesConfig attributesConfig;
    @Resource
    private UserAttributesConverter userAttributesConverter;
    protected GenericAttributesService<T> attributesService;

    public Response getAttributesOfRecipient(int startIndex, int maxRecords, T recipient, Set<String> attrNames) throws RemoteException {
        List<ProfileAttribute> attributes = getAttributes(recipient, attrNames);

        int totalCount = attributes.size();

        if (totalCount < startIndex) {
            attributes.clear();
        } else {
            if (maxRecords != 0) {
                if (startIndex + maxRecords > totalCount) {
                    attributes = attributes.subList(startIndex, totalCount);
                } else {
                    attributes = attributes.subList(startIndex, startIndex + maxRecords);
                }
            } else {
                if (startIndex > 0) {
                    attributes = attributes.subList(startIndex, totalCount);
                }
            }
        }
        List<ClientUserAttribute> clientUserAttributes = new ArrayList<ClientUserAttribute>(attributes.size());
        for (ProfileAttribute pa : attributes) {
            clientUserAttributes.add(userAttributesConverter.toClient(pa, null));
        }

        Response response;
        if (attributes.size() == 0) {
            response = Response.status(Response.Status.NO_CONTENT)
                    .header(RestConstants.HEADER_START_INDEX, startIndex)
                    .header(RestConstants.HEADER_RESULT_COUNT, attributes.size())
                    .header(RestConstants.HEADER_TOTAL_COUNT, totalCount)
                    .build();
        } else {
            response = Response.ok()
                    .entity(new UserAttributesListWrapper(clientUserAttributes))
                    .header(RestConstants.HEADER_START_INDEX, startIndex)
                    .header(RestConstants.HEADER_RESULT_COUNT, attributes.size())
                    .header(RestConstants.HEADER_TOTAL_COUNT, totalCount)
                    .build();
        }

        return response;
    }

    public Response putAttributes(List<ClientUserAttribute> newCollection, T recipient) throws RemoteException {
        for (ClientUserAttribute pa : newCollection) {
            if (isEmpty(pa.getName()) || pa.getName().length() > attributesConfig.getMaxLengthAttrName()) {
                throw new IllegalParameterValueException("name", pa.getName());
            }
            if (isEmpty(pa.getValue()) || pa.getValue().length() > attributesConfig.getMaxLengthAttrValue()) {
                throw new IllegalParameterValueException("value", pa.getValue());
            }
        }
        List<ProfileAttribute> oldCollection = getAttributes(recipient, null);

        for (ProfileAttribute pa : oldCollection) {
            attributesService.deleteAttribute(recipient, pa);
        }

        for (ClientUserAttribute pa : newCollection) {
            attributesService.putAttribute(recipient, userAttributesConverter.toServer(pa, null));
        }

        return Response.ok().build();
    }

    public Response addAttribute(ClientUserAttribute clientUserAttribute, T recipient) throws RemoteException {
        ProfileAttribute pa = userAttributesConverter.toServer(clientUserAttribute, null);

        try {
            if (attributesService.getAttribute(recipient, pa.getAttrName()) != null) {
                throw new ResourceAlreadyExistsException(pa.getAttrName());
            }
        } catch (ServiceException se) {
            throw generateResourceNotFoundException(recipient);
        }

        attributesService.putAttribute(recipient, pa);

        return Response.status(Response.Status.CREATED).entity(pa).build();
    }

    public Response deleteAttributes(T recipient, Set<String> attrNames) throws RemoteException {
        List<ProfileAttribute> list = getAttributes(recipient, attrNames);

        for (ProfileAttribute pa : list) {
            attributesService.deleteAttribute(recipient, pa);
        }
        return Response.noContent().build();
    }

    public Response getSpecificAttributeOfRecipient(T recipient, String attrName)
            throws RemoteException {
        ProfileAttribute attribute = null;

        try {
            attribute = attributesService.getAttribute(recipient, attrName);
        } catch (ServiceException se) {
            throw generateResourceNotFoundException(recipient);
        }

        if (attribute == null) {
            throw new ResourceNotFoundException(attrName);
        }

        return Response.ok(userAttributesConverter.toClient(attribute, null)).build();
    }

    public Response putAttribute(ClientUserAttribute clientUserAttribute, T recipient, String attrName)
            throws RemoteException {
        Response.Status status = Response.Status.OK;
        ProfileAttribute attr = userAttributesConverter.toServer(clientUserAttribute, null);

        if (attr.getAttrName() == null) {
            attr.setAttrName(attrName);
        } else if (isEmpty(attr.getAttrName())) {
            throw new IllegalParameterValueException("name", "<empty>");
        }

        if (attrName.equals(attr.getAttrName())) {
            if (isEmpty(attr.getAttrValue())) {
                throw new IllegalParameterValueException("value", "<empty>");
            }
            if (attributesService.getAttribute(recipient, attr.getAttrName()) == null) {
                status = Response.Status.CREATED;
            }
            attributesService.putAttribute(recipient, attr);
        } else {
            ProfileAttribute existing = attributesService.getAttribute(recipient, attrName);
            if (existing == null) {
                throw new ResourceNotFoundException(attrName);
            }
            if (attr.getAttrValue() == null) {
                attr.setAttrValue(existing.getAttrValue());
            }
            attributesService.putAttribute(recipient, attr);
            attributesService.deleteAttribute(recipient, existing);
        }

        return Response.status(status).build();
    }

    public Response postToAttribute(String name, String attrName) throws RemoteException {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    public Response deleteAttribute(T recipient, String attrName) throws RemoteException {
        ProfileAttributeImpl dto = new ProfileAttributeImpl();
        dto.setAttrName(attrName);

        try {
            attributesService.deleteAttribute(recipient, dto);
        } catch (ServiceException se) {
            if (se.getErrorCode() == ServiceException.RESOURCE_NOT_FOUND) {
                throw new ResourceNotFoundException(attrName);
            }
            throw se;
        }

        return Response.noContent().build();
    }

    private List<ProfileAttribute> getAttributes(T recipient, Set<String> attrNames)
            throws RemoteException {
        List<ProfileAttribute> found, needed;

        try {
            found = attributesService.getAttributes(recipient);
        } catch (ServiceException se) {
            throw generateResourceNotFoundException(recipient);
        }

        if (attrNames != null && !attrNames.isEmpty()) {
            needed = new LinkedList<ProfileAttribute>();
            for (ProfileAttribute pa : found) {
                if (attrNames.contains(pa.getAttrName())) {
                    needed.add(pa);
                }
            }
        } else {
            needed = found;
        }

        return needed;
    }

    private boolean isEmpty(String val) {
        return val == null || empty.matcher(val).matches();
    }

    protected abstract ResourceNotFoundException generateResourceNotFoundException(T recipient);

    public abstract void setAttributesService(GenericAttributesService<T> attributesService);
}
