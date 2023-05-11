package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class UnauthorizedException extends BackendException {

  public UnauthorizedException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.UNAUTHORIZED.code());
  }

  public UnauthorizedException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.UNAUTHORIZED.code());
  }
}
