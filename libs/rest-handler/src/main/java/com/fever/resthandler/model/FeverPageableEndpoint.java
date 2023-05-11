package com.fever.resthandler.model;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeverPageableEndpoint<T, R> extends FeverEndpoint<T> {

  private final Function<RoutingContext, Single<Page<R>>> action;
  private final Function<Page<R>, T> adaptFunction;
  private final Function<Page<R>, HttpResponseStatus> resultHttpResponseStatus;
  private final Function<Page<R>, Map<String, String>> headers;

  public FeverPageableEndpoint(final HttpMethod httpMethod,
                                        final String endpointUrl,
                                        final Consumer<RoutingContext> validation,
                                        final Function<RoutingContext, Single<Page<R>>> action,
                                        final Function<Page<R>, T> adaptFunction,
                                        final Function<Page<R>, Map<String, String>> headers,
                                        final Function<Page<R>, HttpResponseStatus> resultHttpResponseStatus,
                                        final String contentTypeValue,
                                        final Function<T, String> adaptBody,
                                        final String encoding) {
    super(httpMethod, endpointUrl, validation, contentTypeValue, adaptBody, encoding);
    this.action = action;
    this.adaptFunction = adaptFunction;
    this.resultHttpResponseStatus = resultHttpResponseStatus;
    this.headers = headers;
  }

  @Override
  public void action(final RoutingContext routingContext, boolean isDebugEnabled) {
    action.apply(routingContext)
        .subscribe(
            response -> super.makeResponse(
                routingContext,
                adaptFunction.apply(response),
                headers.apply(response),
                resultHttpResponseStatus.apply(response)),
            routingContext::fail);
  }
}
