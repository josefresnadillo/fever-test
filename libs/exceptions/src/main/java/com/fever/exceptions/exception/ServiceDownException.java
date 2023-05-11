package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class ServiceDownException extends BackendException {

  public ServiceDownException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.SERVICE_UNAVAILABLE.code());
  }

  public ServiceDownException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.SERVICE_UNAVAILABLE.code());
  }
}
