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

import com.jaspersoft.jasperserver.api.metadata.user.domain.Tenant;
import com.jaspersoft.jasperserver.api.metadata.user.service.TenantService;
import com.jaspersoft.jasperserver.remote.ServiceException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Volodya Sabadosh
 * @version $Id: $
 */
@Component("tenantAttributesService")
public class TenantAttributesServiceImpl extends GenericAttributesServiceImpl<Tenant> {
    @Resource(name = "concreteTenantService")
    protected TenantService tenantService;

    @Override
    protected Tenant getPersistentPrincipal(Tenant principal) throws ServiceException{
        Tenant tenant = tenantService.getTenant(null, principal.getId());
        if (tenant == null) {
            throw new ServiceException(ServiceException.RESOURCE_NOT_FOUND, "tenant: " + principal.getTenantName() + " not found ");
        }
        return tenant;
    }

}
