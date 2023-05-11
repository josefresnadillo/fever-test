package com.fever.events.primary.handler;

import com.fever.events.primary.api.SearchEvents200Response;
import com.fever.resthandler.DefaultRestHandler;
import io.reactivex.Single;
import io.vertx.reactivex.ext.web.RoutingContext;

public interface EventsRestHandler extends DefaultRestHandler {

  Single<SearchEvents200Response> listEventsByCriteria(final RoutingContext routingContext);
}