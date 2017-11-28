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

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.util.StdDateFormat;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JacksonMapperProvider.java 62954 2016-05-01 09:49:23Z ykovalch $
 */
@Service
public class JacksonMapperProvider {
    private final ObjectMapper mapper;

    public JacksonMapperProvider(){
        mapper = new ObjectMapper();
        AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
        AnnotationIntrospector secondary =  new JacksonAnnotationIntrospector();
        AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
        mapper.setAnnotationIntrospector(pair);
        // Serialize dates using ISO8601 format
        // Jackson uses timestamps by default, so use StdDateFormat to get ISO8601
        mapper.getSerializationConfig().withDateFormat(new StdDateFormat());
        // Deserialize dates using ISO8601 format
        mapper.getDeserializationConfig().withDateFormat(new StdDateFormat());
        // Prevent exceptions from being thrown for unknown properties
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // ignore fields with null values
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }
    public ObjectMapper getObjectMapper(){
        return mapper;
    }
}
