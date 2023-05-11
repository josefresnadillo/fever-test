package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class MethodNotAllowedException extends BackendException {

  public MethodNotAllowedException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.METHOD_NOT_ALLOWED.code());
  }

  public MethodNotAllowedException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.METHOD_NOT_ALLOWED.code());
  }
}
