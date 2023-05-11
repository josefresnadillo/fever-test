package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class InternalServerErrorException extends BackendException {

  public InternalServerErrorException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
  }

  public InternalServerErrorException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
  }
}
