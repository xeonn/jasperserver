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
package com.jaspersoft.jasperserver.jaxrs.common;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Providers;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JaxrsEntityParser.java 42684 2014-03-06 14:26:22Z ykovalchyk $
 */
public class JaxrsEntityParser {
    private final Providers providers;
    private final HttpHeaders httpHeaders;

    private JaxrsEntityParser(Providers providers, HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
        this.providers = providers;
    }

    public static JaxrsEntityParser newInstance(Providers providers, HttpHeaders httpHeaders) {
        if (providers == null || httpHeaders == null) {
            throw new NullPointerException();
        }
        return new JaxrsEntityParser(providers, httpHeaders);
    }

    public <T> T parseEntity(Class<T> clazz, InputStream entityStream, MediaType mediaType) throws IOException {
        // code below comes from com.sun.jersey.multipart.BodyPart#getEntityAs(Class<T> clazz)
        Annotation annotations[] = new Annotation[0];
        MessageBodyReader<T> reader =
                providers.getMessageBodyReader(clazz, clazz, annotations, mediaType);
        if (reader == null) {
            throw new IllegalArgumentException("No available MessageBodyReader for class " + clazz.getName()
                    + " and media type " + mediaType);
        }
        return reader.readFrom(clazz, clazz, annotations, mediaType, httpHeaders.getRequestHeaders(), entityStream);
    }
}
