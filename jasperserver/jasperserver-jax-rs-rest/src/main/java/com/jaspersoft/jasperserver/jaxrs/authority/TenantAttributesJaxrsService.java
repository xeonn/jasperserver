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
import com.jaspersoft.jasperserver.api.metadata.user.domain.client.TenantImpl;
import com.jaspersoft.jasperserver.api.metadata.user.service.TenantService;
import com.jaspersoft.jasperserver.remote.exception.ResourceNotFoundException;
import com.jaspersoft.jasperserver.remote.services.GenericAttributesService;
import static org.apache.commons.lang.StringUtils.isEmpty;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author Volodya Sabadosh
 * @version $Id: TenantAttributesJaxrsService.java 50011 2014-10-09 16:57:26Z vzavadskii $
 */
@Component("tenantAttributesJaxrsService")
public class TenantAttributesJaxrsService extends GenericAttributesJaxrsService<Tenant> {
    @Override
    protected ResourceNotFoundException generateResourceNotFoundException(Tenant recipient) {
        return new ResourceNotFoundException(recipient.getTenantName());
    }

    @Override
    @Resource(name = "tenantAttributesService")
    public void setAttributesService(GenericAttributesService<Tenant> attributesService) {
        super.attributesService = attributesService;
    }

    public Tenant generateRecipient(String tenantId) {
        Tenant tenant = new TenantImpl();
        if (isEmpty(tenantId)) {
            tenantId = TenantService.ORGANIZATIONS;
        }
        tenant.setId(tenantId);
        return tenant;
    }

}
