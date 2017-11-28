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
package com.jaspersoft.jasperserver.remote.helpers;

import com.jaspersoft.jasperserver.api.metadata.common.service.JsonMarshaller;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.remote.exception.RemoteException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JacksonJsonMarshaller.java 65088 2016-11-03 23:22:01Z gbacon $
 */
@Service
public class JacksonJsonMarshaller implements JsonMarshaller {
    @Resource
    private JacksonMapperProvider jacksonMapperProvider;

    @Override
    public <T> T fromJson(String json, Class<T> toClass) {
        try {
            return jacksonMapperProvider.getObjectMapper().reader(toClass).readValue(json);
        } catch (IOException e) {
            throw new RemoteException(new ErrorDescriptor().setErrorCode("json.parse")
                    .setMessage("Unable to parse JSON string to " + toClass.getName())
                    .setParameters(toClass.getName(), json), e);
        }
    }

    @Override
    public String toJson(Object object) {
        final Class<?> objectClass = object.getClass();
        try {
            return jacksonMapperProvider.getObjectMapper().writer().writeValueAsString(object);
        } catch (IOException e) {
            throw new RemoteException(new ErrorDescriptor().setErrorCode("object.to.json")
                    .setMessage("Unable to marshal " + objectClass + " to JSON string")
                    .setParameters(objectClass.getName(), object.toString()));
        }
    }
}
