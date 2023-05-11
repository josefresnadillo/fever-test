package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class ConflictException extends BackendException {

  public ConflictException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.CONFLICT.code());
  }

  public ConflictException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.CONFLICT.code());
  }
}
