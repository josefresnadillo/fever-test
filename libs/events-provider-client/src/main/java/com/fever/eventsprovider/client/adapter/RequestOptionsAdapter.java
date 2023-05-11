package com.fever.eventsprovider.client.adapter;

import com.fever.restclient.model.RequestOptions;

public final class RequestOptionsAdapter {

  private static final int DEFAULT_RETRIES = 0;

  private RequestOptionsAdapter() {
    // do nothing
  }

  public static RequestOptions.Builder adapt(final String url,
                                             final String name) {
    return RequestOptions
        .builder(url, name)
        .withRetries(DEFAULT_RETRIES);
  }
}
