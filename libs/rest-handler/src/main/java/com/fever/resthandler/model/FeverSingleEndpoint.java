package com.fever.resthandler.model;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeverSingleEndpoint<T> extends FeverEndpoint<T> {

    private final Function<RoutingContext, Single<T>> action;
    private final HttpResponseStatus responseStatus;

    public FeverSingleEndpoint(final HttpMethod httpMethod,
                               final String endpointUrl,
                               final Consumer<RoutingContext> validation,
                               final Function<RoutingContext, Single<T>> action,
                               final HttpResponseStatus responseStatus,
                               final String contentTypeValue,
                               final Function<T, String> adaptBody,
                               final String encoding) {
        super(httpMethod, endpointUrl, validation, contentTypeValue, adaptBody, encoding);
        this.action = action;
        this.responseStatus = responseStatus;
    }

    public void action(final RoutingContext routingContext,
                       final boolean isDebugEnabled) {
        action.apply(routingContext)
                .subscribe(
                        response -> super.makeResponse(
                                routingContext,
                                response,
                                Map.of(),
                                responseStatus),
                        routingContext::fail);
    }
}
