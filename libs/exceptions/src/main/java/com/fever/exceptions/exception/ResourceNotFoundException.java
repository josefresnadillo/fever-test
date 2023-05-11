package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class ResourceNotFoundException extends BackendException {

  public ResourceNotFoundException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.NOT_FOUND.code());
  }

  public ResourceNotFoundException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.NOT_FOUND.code());
  }
}