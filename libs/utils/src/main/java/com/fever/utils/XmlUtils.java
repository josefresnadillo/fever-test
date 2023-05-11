package com.fever.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class XmlUtils {

    private XmlUtils() {
        // do nothing
    }

    public static <T> T readBodyIgnoreUnknowns(final String body,
                                               final Class<T> classz,
                                               final JavaTimeModule javaTimeModule) {
        try {
            final XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            xmlMapper.registerModule(javaTimeModule);
            return xmlMapper.readValue(body, classz);
        } catch (Exception e) {
            throw new RuntimeException("Could not parse body: " + e.getMessage());
        }
    }

}
