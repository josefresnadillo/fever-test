package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class NotAcceptableException extends BackendException {

  public NotAcceptableException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.NOT_ACCEPTABLE.code());
  }

  public NotAcceptableException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.NOT_ACCEPTABLE.code());
  }
}
