package com.fever.exceptions.domain.model;

import java.util.Objects;

public class ErrorMessage {

    private final FeverErrorType code;

    private final String message;

    public ErrorMessage(final FeverErrorType code, final String message) {
        this.code = code;
        this.message = message;
    }

    public FeverErrorType getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return code == that.code && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}

