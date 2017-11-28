/*
* Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights  reserved.
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

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: ExportExecutionRejectedException.java 45722 2014-05-14 10:24:22Z sergey.prilukin $
 */
public class ExportExecutionRejectedException extends RemoteException {
    public static final String ERROR_CODE_EXPORT_EXECUTION_REJECTED = "export.execution.rejected";

    public ExportExecutionRejectedException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
        if(errorDescriptor.getErrorCode() == null){
            this.getErrorDescriptor().setErrorCode(ERROR_CODE_EXPORT_EXECUTION_REJECTED);
        }
    }

    public ExportExecutionRejectedException(String message){
        super(message);
        getErrorDescriptor().setErrorCode(ERROR_CODE_EXPORT_EXECUTION_REJECTED);
    }
}
