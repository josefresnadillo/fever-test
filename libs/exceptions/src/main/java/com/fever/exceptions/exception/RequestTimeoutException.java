package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class RequestTimeoutException extends BackendException {

  public RequestTimeoutException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.REQUEST_TIMEOUT.code());
  }

  public RequestTimeoutException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.REQUEST_TIMEOUT.code());
  }
}
