package com.jaspersoft.jasperserver.search.common;

import com.jaspersoft.jasperserver.search.util.JSONConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author schubar
 */
public class ResourceDetailsSerializer extends JsonSerializer<ResourceDetails> {

    @Override
    public void serialize(ResourceDetails value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        String desc = value.getDescription();

        jgen.writeStartObject();

        jgen.writeFieldName(JSONConverter.RESOURCE_NAME);
        jgen.writeString(value.getName());
        jgen.writeFieldName(JSONConverter.RESOURCE_LABEL);
        jgen.writeString(value.getLabel());
        jgen.writeFieldName(JSONConverter.RESOURCE_DESC);
        jgen.writeString((desc != null) ? desc.replace("\\n", "<br>") : "");
        jgen.writeFieldName(JSONConverter.RESOURCE_URI);
        jgen.writeString(value.getURI());
        jgen.writeFieldName(JSONConverter.RESOURCE_URI_STRING);
        jgen.writeString(value.getURIString());

        jgen.writeFieldName(JSONConverter.RESOURCE_PARENT_URI);
        jgen.writeString(value.getParentURI());
        jgen.writeFieldName(JSONConverter.RESOURCE_PARENT_FOLDER);
        jgen.writeString(value.getParentFolder());

        jgen.writeFieldName(JSONConverter.RESOURCE_RESOURCE_TYPE);
        jgen.writeString(value.getResourceType());

        jgen.writeFieldName(JSONConverter.RESOURCE_PERMISSIONS);
        jgen.writeString(JSONConverter.getPermissionsMask(value));

        jgen.writeEndObject();
    }
}
