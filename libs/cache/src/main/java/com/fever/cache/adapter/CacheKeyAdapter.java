package com.fever.cache.adapter;

import java.util.Map;
import java.util.stream.Collectors;

public class CacheKeyAdapter {

  private static final String CACHE_KEY_DELIMITER = ":";

  private CacheKeyAdapter() {
    // do nothing
  }

  public static String calculateCacheKey(final Map<String, Object> requestPathParams,
                                         final Map<String, Object> requestQueryParams,
                                         final String prefixCache,
                                         final String requestName) {
    final String pathParams = requestPathParams
        .values()
        .stream()
        .map(String::valueOf)
        .map(String::toLowerCase)
        .sorted()
        .collect(Collectors.joining(CACHE_KEY_DELIMITER));

    final String queryParams = requestQueryParams
        .values()
        .stream()
        .map(String::valueOf)
        .map(String::toLowerCase)
        .sorted()
        .collect(Collectors.joining(CACHE_KEY_DELIMITER));

    String key = prefixCache + CACHE_KEY_DELIMITER + requestName;
    if (!pathParams.isEmpty()) {
      key = key + CACHE_KEY_DELIMITER + pathParams;
    }

    if (!queryParams.isEmpty()) {
      key = key + CACHE_KEY_DELIMITER + queryParams;
    }

    return key;
  }
}
