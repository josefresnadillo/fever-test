package com.fever.restclient.exceptions;

public class HttpMethodException extends RuntimeException {
  public HttpMethodException(Exception cause) {
    super(cause);
  }
}
