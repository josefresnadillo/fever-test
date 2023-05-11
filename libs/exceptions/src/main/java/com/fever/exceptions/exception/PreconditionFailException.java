package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class PreconditionFailException extends BackendException {

  public PreconditionFailException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.PRECONDITION_FAILED.code());
  }

  public PreconditionFailException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.PRECONDITION_FAILED.code());
  }
}
