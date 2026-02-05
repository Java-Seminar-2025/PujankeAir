package com.air.pujanke.utility;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// To avoid boilerplate of configuring jackson every time I need to map something.
public class ConfiguredObjectMapper {

    private ConfiguredObjectMapper() {}

    public static ObjectMapper getMapper() {
        var mapper = new ObjectMapper();

        // Because it complained when I tried to serialize "Instant"
        mapper.registerModule(new JavaTimeModule());

        // Because it fails if the object that's being mapped has properties that the resulting object doesn't
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }
}
