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
package com.jaspersoft.jasperserver.remote.common;

import com.jaspersoft.jasperserver.remote.exception.RemoteException;

/**
 * Use implementation of this interface to call remote service within template.
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: RemoteServiceInTemplateCaller.java 21936 2012-01-25 13:39:03Z ykovalchyk $
 */
public interface RemoteServiceInTemplateCaller<CallResultType, RemoteServiceType> {
    CallResultType call(RemoteServiceType remoteService) throws RemoteException;
}
