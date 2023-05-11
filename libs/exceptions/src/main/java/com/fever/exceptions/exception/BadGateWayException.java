package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class BadGateWayException extends BackendException {

  public BadGateWayException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.BAD_GATEWAY.code());
  }

  public BadGateWayException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.BAD_GATEWAY.code());
  }
}
