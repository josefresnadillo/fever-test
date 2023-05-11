package com.fever.eventsprovider.client.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDeserializer extends JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter formatter;

    public CustomDeserializer(final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalDateTime deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(parser.getText(), formatter);
    }
}