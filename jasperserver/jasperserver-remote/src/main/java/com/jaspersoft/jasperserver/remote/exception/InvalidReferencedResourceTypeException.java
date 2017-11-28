/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
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
package com.jaspersoft.jasperserver.remote.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: InvalidReferencedResourceTypeException.java 62954 2016-05-01 09:49:23Z ykovalch $
 */
public class InvalidReferencedResourceTypeException extends RemoteException {
    private static final String ERROR_CODE  = "invalid.reference.type";
    public InvalidReferencedResourceTypeException(String currentType, String referenceAttributeName, String uri, String ... expectedTypes){
        super(buildMessage(currentType, referenceAttributeName, uri, expectedTypes));
        getErrorDescriptor().setErrorCode(ERROR_CODE).setParameters(currentType, referenceAttributeName, uri,
                StringUtils.join(expectedTypes, ", "));
    }

    private static String buildMessage(String currentType, String referenceAttributeName, String uri, String ... expectedTypes){
        return "Resource or type '" + currentType + "', referenced by an attribute '" + referenceAttributeName
                + "' (" + uri + "), doesn't match expected types: " + StringUtils.join(expectedTypes, ", ") + ".";
    }
}
