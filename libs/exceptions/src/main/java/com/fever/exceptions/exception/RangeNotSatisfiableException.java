package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class RangeNotSatisfiableException extends BackendException {

  public RangeNotSatisfiableException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE.code());
  }

  public RangeNotSatisfiableException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE.code());
  }
}
