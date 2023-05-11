package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class RateLimitExceededException extends BackendException {

  public RateLimitExceededException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.TOO_MANY_REQUESTS.code());
  }

  public RateLimitExceededException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.TOO_MANY_REQUESTS.code());
  }
}
