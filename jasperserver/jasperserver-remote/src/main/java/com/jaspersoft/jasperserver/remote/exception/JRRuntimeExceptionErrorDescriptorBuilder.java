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

import com.jaspersoft.jasperserver.remote.exception.xml.ErrorDescriptor;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketingService;
import net.sf.jasperreports.engine.JRRuntimeException;
import org.springframework.stereotype.Component;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JRRuntimeExceptionErrorDescriptorBuilder.java 55164 2015-05-06 20:54:37Z mchan $
 */
@Component
public class JRRuntimeExceptionErrorDescriptorBuilder implements ErrorDescriptorBuilder<JRRuntimeException> {
    @Override
    public ErrorDescriptor build(JRRuntimeException e) {
        ErrorDescriptor descriptor = new ErrorDescriptor(e);
        final String messageKey = e.getMessageKey();
        if(BucketingService.EXCEPTION_MESSAGE_KEY_BUCKET_MEASURE_LIMIT.equals(messageKey)){
            descriptor = new ErrorDescriptor.Builder().setErrorCode("crosstab.bucket.measure.limit").setMessage(e.getMessage())
                    .getErrorDescriptor();
        }
        return descriptor;
    }
}
