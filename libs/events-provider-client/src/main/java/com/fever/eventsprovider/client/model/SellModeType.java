package com.fever.eventsprovider.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum SellModeType {
    ONLINE("online"),
    OFFLINE("offline");

    private String value;

    SellModeType(String value) {
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
    public static SellModeType fromValue(String value) {
        return Arrays.stream(SellModeType.values())
                .filter(sellModeType -> sellModeType.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unexpected value '" + value + "'"));
    }
}
