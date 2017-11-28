/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ResourceNotFoundException.java 49286 2014-09-23 13:32:25Z ykovalchyk $
 */
public class ResourceNotFoundException extends RemoteException {
    public static final String ERROR_CODE_RESOURCE_NOT_FOUND = "resource.not.found";
    public ResourceNotFoundException() {
        super();
        getErrorDescriptor().setErrorCode(ERROR_CODE_RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super("Resource " + message + " not found");
        ErrorDescriptor errorDescriptor = getErrorDescriptor();
        errorDescriptor.setErrorCode(ERROR_CODE_RESOURCE_NOT_FOUND);
        errorDescriptor.setParameters(message);
    }

    public ResourceNotFoundException(String message, String id) {
        super(message);
        ErrorDescriptor errorDescriptor = getErrorDescriptor();
        errorDescriptor.setErrorCode(ERROR_CODE_RESOURCE_NOT_FOUND);
        errorDescriptor.setParameters(id);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super("Resource " + message + " not found", cause);
        getErrorDescriptor().setErrorCode(ERROR_CODE_RESOURCE_NOT_FOUND);
        ErrorDescriptor errorDescriptor = getErrorDescriptor();
        errorDescriptor.setParameters(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
        getErrorDescriptor().setErrorCode(ERROR_CODE_RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(ErrorDescriptor errorDescriptor){
        super(errorDescriptor);
    }
}
