package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class BadRequestException extends BackendException {

  public BadRequestException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.BAD_REQUEST.code());
  }

  public BadRequestException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.BAD_REQUEST.code());
  }
}
