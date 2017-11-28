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
package com.jaspersoft.jasperserver.remote.connection.datadiscovery;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: QueryExecutionException.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
public class QueryExecutionException extends RemoteException {

    public QueryExecutionException(Object query, Class<?> connectionClass, Exception e){
        this("Query execution failed", query, connectionClass, e);
    }

    public QueryExecutionException(String message, Object query, Class<?> connectionClass, Exception e){
        super(new ErrorDescriptor().setErrorCode("connection.query.failed")
                .setMessage(message).setParameters(query.toString(), connectionClass.getName()), e);
    }
}