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

import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchCriteria;
import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchResult;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.Relation;
import com.jaspersoft.jasperserver.jaxrs.common.RestConstants;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.exception.ResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentity;
import com.jaspersoft.jasperserver.remote.resources.converters.HypermediaOptions;
import com.jaspersoft.jasperserver.remote.services.AttributesService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Volodya Sabadosh
 * @version $Id: AttributesJaxrsService.java 54590 2015-04-22 17:55:42Z vzavadsk $
 */
@Component("attributesJaxrsService")
public class AttributesJaxrsService {
    public static String HAL_FORMAT = "hal+";
    private Pattern empty = Pattern.compile("^\\s*$");

    @Resource
    protected AttributesService attributesService;

    public Response getAttributes(AttributesSearchCriteria searchCriteria, HypermediaOptions hypermediaOptions) throws RemoteException {
        AttributesSearchResult<ClientUserAttribute> result = attributesService.getAttributes(searchCriteria, includePermissions(hypermediaOptions));

        if (result.getList().size() > 0) {
            return Response.ok()
                    .entity(new HypermediaAttributesListWrapper(result.getList()))
                    .header(RestConstants.HEADER_START_INDEX, searchCriteria.getStartIndex())
                    .header(RestConstants.HEADER_RESULT_COUNT, result.getList().size())
                    .header(RestConstants.HEADER_TOTAL_COUNT, result.getTotalCount())
                    .build();
        } else {
            return Response.noContent()
                    .header(RestConstants.HEADER_START_INDEX, searchCriteria.getStartIndex())
                    .header(RestConstants.HEADER_RESULT_COUNT, 0)
                    .header(RestConstants.HEADER_TOTAL_COUNT, result.getTotalCount())
                    .build();
        }
    }

    public Response getAttributesOfRecipient(RecipientIdentity recipientIdentity, Set<String> names, HypermediaOptions hypermediaOptions) throws RemoteException {
        List<ClientUserAttribute> result = attributesService.getAttributes(recipientIdentity, names, includePermissions(hypermediaOptions));

        if (result.size() > 0) {
            return Response.ok().entity(new HypermediaAttributesListWrapper(result)).build();
        } else {
            return Response.noContent().build();
        }
    }

    public Response putAttributes(List<HypermediaAttribute> newCollection, RecipientIdentity recipientIdentity,
                                  Set<String> attrNames, HypermediaOptions hypermediaOptions, String mediaType) throws RemoteException {
        if (!mediaType.contains(HAL_FORMAT)) {
            for (HypermediaAttribute hypermediaAttribute : newCollection) {
                hypermediaAttribute.setEmbedded(null);
            }
        }

        List<ClientUserAttribute> result = attributesService.putAttributes(recipientIdentity,
                newCollection, attrNames, includePermissions(hypermediaOptions));

        return Response.ok().entity(new HypermediaAttributesListWrapper(result)).build();
    }

    public Response putAttribute(HypermediaAttribute client, RecipientIdentity recipientIdentity, String attrName,
                                 HypermediaOptions hypermediaOptions, String mediaType) throws RemoteException {
        if (!mediaType.contains(HAL_FORMAT)) {
            client.setEmbedded(null);
        }

        List<ClientUserAttribute> existingAttributes = attributesService.getAttributes(recipientIdentity, Collections.singleton(attrName), false);

        if (client.getName() == null) {
            client.setName(attrName);
        } else if (isEmpty(client.getName())) {
            throw new IllegalParameterValueException("name", "<empty>");
        }

        ClientUserAttribute existingAttr = null;
        if (existingAttributes.size() > 0) {
            existingAttr = existingAttributes.get(0);
        }

        Set<String> effectedAttrNames = new HashSet<String>();
        effectedAttrNames.add(attrName);

        Response.Status status = Response.Status.OK;
        if (attrName.equals(client.getName())) {
            if (existingAttributes.size() == 0) {
                status = Response.Status.CREATED;
            }
        } else {
            if (existingAttr == null) {
                throw new ResourceNotFoundException(attrName);
            }
            effectedAttrNames.add(client.getName());
        }

        List<ClientUserAttribute> result = attributesService.putAttributes(recipientIdentity,
                Arrays.asList(client), effectedAttrNames, includePermissions(hypermediaOptions));

        return Response.status(status).entity(result.get(0)).build();
    }

    public Response deleteAttribute(RecipientIdentity recipientIdentity, String attrName) throws RemoteException {
        List<ClientUserAttribute> existingAttributes = attributesService.getAttributes(recipientIdentity,
                Collections.singleton(attrName), false);
        if (existingAttributes == null || existingAttributes.size() == 0) {
            throw new ResourceNotFoundException(attrName);
        } else {
            attributesService.deleteAttributes(recipientIdentity,  Collections.singleton(attrName));
        }

        return Response.noContent().build();
    }

    public Response deleteAttributes(RecipientIdentity recipientIdentity,
                                     Set<String> attrNames) throws RemoteException {
        attributesService.deleteAttributes(recipientIdentity, attrNames);

        return Response.noContent().build();
    }

    public Response getSpecificAttributeOfRecipient(RecipientIdentity recipientIdentity,
                                                    String attrName,
                                                    HypermediaOptions hypermediaOptions) throws RemoteException {
        List<ClientUserAttribute> existingAttributes = attributesService.getAttributes(recipientIdentity,
                Collections.singleton(attrName), includePermissions(hypermediaOptions));

        if (existingAttributes.size() == 0) {
            throw new ResourceNotFoundException(attrName);
        }

        return Response.ok(existingAttributes.get(0)).build();
    }

    public HypermediaOptions getHypermediaOptions(String accept, String embeddedParam) {
        HypermediaOptions hypermediaOptions = null;
        Set<Relation> relations = new HashSet<Relation>();

        boolean isHypermediaFormat = accept.contains(HAL_FORMAT);
        if (isHypermediaFormat && embeddedParam != null) {
            hypermediaOptions = new HypermediaOptions();
            Relation relation = Relation.fromString(embeddedParam.toLowerCase());
            if (relation != null) {
                relations.add(relation);
            }
            hypermediaOptions.setEmbedded(relations);
        } else if (isHypermediaFormat) {
            hypermediaOptions = new HypermediaOptions();
            relations.add(Relation.PERMISSION);
            hypermediaOptions.setLinks(relations);
        }

        return hypermediaOptions;
    }

    protected boolean includePermissions(HypermediaOptions hypermediaOptions) {
        return (hypermediaOptions != null && hypermediaOptions.getEmbedded() != null &&
                hypermediaOptions.getEmbedded().contains(Relation.PERMISSION));
    }

    private boolean isEmpty(String val) {
        return val == null || empty.matcher(val).matches();
    }

}
