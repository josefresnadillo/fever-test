package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class UnexpectedException extends BackendException {

  public UnexpectedException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
  }

  public UnexpectedException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
  }
}
