package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;

import java.util.List;

public abstract class BackendException extends RuntimeException {
    private final List<ErrorMessage> errorMessageList;
    private final int httpCode;

    protected BackendException(final List<ErrorMessage> errorMessages,
                               final int httpCode) {
        this.errorMessageList = errorMessages;
        this.httpCode = httpCode;
    }

    protected BackendException(final FeverErrorType detailMsgCode,
                               final String detailMsg,
                               final int httpCode) {
        super(detailMsg);
        this.errorMessageList = List.of(new ErrorMessage(detailMsgCode, detailMsg));
        this.httpCode = httpCode;
    }

    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public ErrorMessage getFirstErrorMessage() {
        return getErrorMessageList()
                .stream()
                .findFirst()
                .orElse(new ErrorMessage(FeverErrorType.UNKNOWN, ""));
    }
}
