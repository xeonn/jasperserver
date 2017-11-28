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
package com.jaspersoft.jasperserver.remote.services;

import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchCriteria;
import com.jaspersoft.jasperserver.api.metadata.user.service.AttributesSearchResult;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import com.jaspersoft.jasperserver.remote.helpers.RecipientIdentity;

import java.util.List;
import java.util.Set;

/**
 * @author Volodya Sabadosh
 * @version $Id: AttributesService.java 54590 2015-04-22 17:55:42Z vzavadsk $
 */
public interface AttributesService {
    void deleteAttributes(RecipientIdentity holder, Set<String> attrNames) throws RemoteException;

    List<ClientUserAttribute> putAttributes(RecipientIdentity recipientIdentity, List<? extends ClientUserAttribute> newCollection,
                                            Set<String> names, boolean includeEffectivePermissions) throws RemoteException;

    AttributesSearchResult<ClientUserAttribute>
    getAttributes(AttributesSearchCriteria searchCriteria, boolean includeEffectivePermissions) throws RemoteException;

    List<ClientUserAttribute> getAttributes(RecipientIdentity holder, Set<String> names, boolean includeEffectivePermissions) throws RemoteException;

}
