package com.fever.exceptions.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum FeverErrorType {

    DATE_NOT_VALID("DATE_NOT_VALID"),

    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),

    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),

    FORBIDDEN_USER("FORBIDDEN_USER"),

    UNKNOWN("UNKNOWN");

    private String value;

    FeverErrorType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static FeverErrorType fromValue(final String value) {
        return Arrays.stream(FeverErrorType.values())
                .filter(feverErrorType -> feverErrorType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unexpected value '" + value + "'"));
    }
}

