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

import com.jaspersoft.jasperserver.api.metadata.user.domain.Tenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.common.RestConstants;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * @author Volodya Sabadosh
 * @version $Id: ServerAttributesJaxrsService.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
@Service
@Path("/attributes")
public class ServerAttributesJaxrsService {
    @Resource(name = "tenantAttributesJaxrsService")
    private TenantAttributesJaxrsService attributeService;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAttributes(@QueryParam("name") Set<String> attrNames,
                                  @QueryParam(RestConstants.QUERY_PARAM_OFFSET) Integer startIndex,
                                  @QueryParam(RestConstants.QUERY_PARAM_LIMIT) Integer limit) throws RemoteException {
        Tenant recipient = attributeService.generateRecipient(null);
        return attributeService.getAttributesOfRecipient(startIndex == null ? 0 : startIndex, limit == null ? 0 : limit, recipient, attrNames);
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putAttributes(UserAttributesListWrapper newCollection) throws RemoteException {
        Tenant recipient = attributeService.generateRecipient(null);
        return attributeService.putAttributes(newCollection.getProfileAttributes(), recipient);
    }

    @DELETE
    public Response deleteAttributes (@QueryParam("name") Set<String> attrNames) throws RemoteException {
        Tenant recipient = attributeService.generateRecipient(null);
        return attributeService.deleteAttributes(recipient, attrNames);
    }

    @GET
    @Path("/{attrName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSpecificAttribute(@PathParam("attrName") String attrName) throws RemoteException {
        Tenant recipient = attributeService.generateRecipient(null);
        return attributeService.getSpecificAttributeOfRecipient(recipient, attrName);
    }

    @PUT
    @Path("/{attrName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putAttribute(ClientUserAttribute attr, @PathParam("attrName") String attrName) throws RemoteException {
        Tenant recipient = attributeService.generateRecipient(null);
        return attributeService.putAttribute(attr, recipient, attrName);
    }

    @DELETE
    @Path("/{attrName}")
    public Response deleteAttribute(@PathParam("attrName") String attrName) throws RemoteException {
        Tenant recipient = attributeService.generateRecipient(null);
        return attributeService.deleteAttribute(recipient, attrName);
    }

}
