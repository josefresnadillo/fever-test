package com.fever.exceptions.exception;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.List;

public class MediaTypeException extends BackendException {

  public MediaTypeException(final List<ErrorMessage> errorMessages) {
    super(errorMessages, HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.code());
  }

  public MediaTypeException(final FeverErrorType detailMsgCode, final String detailMsg) {
    super(detailMsgCode,
        detailMsg,
        HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.code());
  }
}
