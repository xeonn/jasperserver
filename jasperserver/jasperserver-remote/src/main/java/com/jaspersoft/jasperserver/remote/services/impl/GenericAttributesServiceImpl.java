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

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;
import com.jaspersoft.jasperserver.api.metadata.user.service.ProfileAttributeService;
import com.jaspersoft.jasperserver.remote.ServiceException;
import com.jaspersoft.jasperserver.remote.exception.IllegalParameterValueException;
import com.jaspersoft.jasperserver.remote.services.GenericAttributesService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Volodya Sabadosh
 * @version $Id: $
 */
public abstract class GenericAttributesServiceImpl<T> implements GenericAttributesService<T> {

    @Resource
    protected ProfileAttributeService profileAttributeService;

    @Override
    public void deleteAttribute(T principal, ProfileAttribute pa) throws ServiceException {
        T persistentPrincipal = getPersistentPrincipal(principal);

        @SuppressWarnings("unchecked")
        List<ProfileAttribute> l = profileAttributeService.getProfileAttributesForPrincipal(null, persistentPrincipal);

        for (ProfileAttribute att:l ){
            if (att.getAttrName().equals(pa.getAttrName())){
                att.setPrincipal(persistentPrincipal);
                profileAttributeService.deleteProfileAttribute(null, att);
                return;
            }
        }

        throw new ServiceException(ServiceException.RESOURCE_NOT_FOUND, "attribute with key=" + pa.getAttrName() +
                " does not exist");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProfileAttribute> getAttributes(T principal) throws ServiceException {
        T persistentPrincipal = getPersistentPrincipal(principal);
        return profileAttributeService.getProfileAttributesForPrincipal(null,
                persistentPrincipal, false);
    }

    @Override
    public ProfileAttribute getAttribute(T principal, String name) throws ServiceException {
        List<ProfileAttribute> attributes = getAttributes(principal);
        for (ProfileAttribute p : attributes){
            if (p.getAttrName().equals(name)) return p;
        }
        return null;
    }

    @Override
    public void putAttribute(T principal, ProfileAttribute pa) throws IllegalParameterValueException {
        T persistentPrincipal = getPersistentPrincipal(principal);
        pa.setPrincipal(persistentPrincipal);
        profileAttributeService.putProfileAttribute(null, pa);
    }

    protected abstract T getPersistentPrincipal(T principal) throws ServiceException;

}
