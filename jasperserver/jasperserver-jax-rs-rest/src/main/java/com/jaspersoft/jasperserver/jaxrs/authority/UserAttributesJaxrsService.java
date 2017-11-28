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

import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.UserImpl;
import com.jaspersoft.jasperserver.remote.exception.ResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.services.GenericAttributesService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Volodya Sabadosh
 * @version $Id: UserAttributesJaxrsService.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
@Component("userAttributesJaxrsService")
public class UserAttributesJaxrsService extends GenericAttributesJaxrsService<User> {
    @Override
    protected ResourceNotFoundException generateResourceNotFoundException(User recipient) {
        return new ResourceNotFoundException(recipient.getUsername());
    }

    @Override
    @Resource(name = "concreteUserAttributesService")
    public void setAttributesService(GenericAttributesService<User> attributesService) {
        super.attributesService = attributesService;
    }

    public User generateRecipient(String userName, String tenantId) {
        User user = new UserImpl();
        user.setUsername(userName);
        user.setTenantId(tenantId);
        return user;
    }
}
