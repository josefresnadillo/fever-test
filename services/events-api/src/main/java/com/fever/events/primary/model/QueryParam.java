package com.fever.events.primary.model;

public enum QueryParam {
    STARTS_AT("starts_at"),
    ENDS_TO("ends_at");

    private final String value;

    QueryParam(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
