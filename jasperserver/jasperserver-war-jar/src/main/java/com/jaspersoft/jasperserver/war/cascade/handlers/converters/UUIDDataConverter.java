package com.jaspersoft.jasperserver.war.cascade.handlers.converters;

import org.springframework.stereotype.Service;

import java.util.UUID;

/*
*  @author inesterenko
*/
@Service
public class UUIDDataConverter implements DataConverter<UUID> {

    @Override
    public UUID stringToValue(String rawData) throws Exception {
        return UUID.fromString(rawData);
    }

    @Override
    public String valueToString(UUID value) {
        return value.toString();
    }
}
