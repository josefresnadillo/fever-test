package com.fever.utils;

public final class EnvUtils {

  private EnvUtils() {
    // do nothing
  }

  public static String findEnv(final String key) {
    return StringUtils.valueOrEmpty(System.getenv(key));
  }
}
