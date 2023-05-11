package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class ForbiddenException extends BackendException {

  public ForbiddenException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.FORBIDDEN.code());
  }

  public ForbiddenException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.FORBIDDEN.code());
  }
}
