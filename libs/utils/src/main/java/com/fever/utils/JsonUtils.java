package com.fever.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
  private static final String BODY_PARSE_ERROR = "Could not parse body: %s";
  private JsonUtils() {
    // do nothing
  }

  public static String toJson(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      return object.toString();
    }
  }
}
