package com.fever.eventsprovider.client.config;

import com.fever.eventsprovider.client.adapter.RequestOptionsAdapter;
import com.fever.restclient.model.RequestOptions;
import com.fever.utils.URIUtils;

import java.util.Map;

public class EventsProviderConfig {

  // GET /api/events
  private static final String GET_LIST_EVENTS_NAME = "listEvents";
  private static final String GET_LIST_EVENTS_PATH = "/api/events";

  private final String baseUrl;
  private final Integer ttl;

  public EventsProviderConfig(final String baseUrl,
                              final Integer ttl) {
    this.baseUrl = baseUrl;
    this.ttl = ttl;
  }

  public Integer getTtl() {
    return ttl;
  }

  // EVENTS

  // GET /api/events
  public RequestOptions listEventsConfig() {
    final String url = URIUtils.adapt(baseUrl, GET_LIST_EVENTS_PATH, Map.of());
    return RequestOptionsAdapter.adapt(url, GET_LIST_EVENTS_NAME)
        .withPathParams(Map.of())
        .withQueryParams(Map.of())
        .build();
  }
}
