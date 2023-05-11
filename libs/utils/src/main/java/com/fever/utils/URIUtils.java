package com.fever.utils;

import java.util.Map;

public final class URIUtils {

  private URIUtils() {
    // do nothing
  }

  public static String adapt(final String baseUrl,
                             final String path) {
    return adapt(baseUrl, path, Map.of(), Map.of());
  }

  public static String adapt(final String baseUrl,
                             final String path,
                             final Map<String, String> pathParams) {
    return adapt(baseUrl, path, pathParams, Map.of());
  }

  public static String adapt(final String baseUrl,
                             final String path,
                             final Map<String, String> pathParams,
                             final Map<String, String> queryParams) {
    String urlPath = path;
    for (Map.Entry<String, String> entry : pathParams.entrySet()) {
      urlPath = urlPath.replace(entry.getKey(), entry.getValue());
    }

    String queries = StringUtils.EMPTY;
    for (Map.Entry<String, String> entry : queryParams.entrySet()) {
      queries = queries.concat(StringUtils.QUERY_CONCAT_STRING + entry.getKey() + StringUtils.QUERY_VALUE_STRING + entry.getValue());
    }
    if(!queryParams.isEmpty()){
      queries = queries.replaceFirst(StringUtils.QUERY_CONCAT_STRING, StringUtils.QUERY_IDENTIFIER_STRING);
    }

    return baseUrl + urlPath + queries;
  }
}
