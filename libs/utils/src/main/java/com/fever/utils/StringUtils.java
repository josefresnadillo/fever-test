package com.fever.utils;

import java.util.Optional;

public final class StringUtils {
  public static final String EMPTY = "";
  public static final String NULL_STRING = "null";
  public static final String QUERY_IDENTIFIER_STRING = "?";
  public static final String QUERY_CONCAT_STRING = "&";
  public static final String QUERY_VALUE_STRING = "=";

  private StringUtils() {
    // do nothing
  }

  public static boolean isNullOrEmptyOrWhiteSpace(final String string) {
    return string == null || string.isEmpty() || string.isBlank() || isStringNull(string);
  }

  public static boolean isNotNullOrEmptyOrWhiteSpace(final String string) {
    return !isNullOrEmptyOrWhiteSpace(string);
  }

  public static String valueOrEmpty(final String value) {
    final String result = Optional.ofNullable(value).orElse(EMPTY);
    return isStringNull(result) ?
        StringUtils.EMPTY:
        result;
  }

  public static boolean isStringNull(final String string) {
    return string != null && string.trim().equalsIgnoreCase(NULL_STRING);
  }
}
