package com.fever.resthandler.model;

public enum Headers {

  TOTAL_COUNT_HEADER("x-total-count");

  private final String value;

  Headers(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
