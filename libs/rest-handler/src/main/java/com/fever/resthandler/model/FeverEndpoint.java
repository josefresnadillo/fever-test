package com.fever.resthandler.model;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class FeverEndpoint<T> {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private final HttpMethod httpMethod;
    private final String endpointUrl;
    private final Consumer<RoutingContext> validation;
    private final String contentTypeValue;
    private final Function<T, String> adaptBody;
    private final String encoding;

    public FeverEndpoint(final HttpMethod httpMethod,
                         final String endpointUrl,
                         final Consumer<RoutingContext> validation,
                         final String contentTypeValue,
                         final Function<T, String> adaptBody,
                         final String encoding) {
        this.httpMethod = httpMethod;
        this.endpointUrl = endpointUrl;
        this.validation = validation;
        this.contentTypeValue = contentTypeValue;
        this.adaptBody = adaptBody;
        this.encoding = encoding;
    }


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void performAction(final RoutingContext routingContext,
                              final boolean isDebugEnabled) {
        validation.accept(routingContext);
        action(routingContext, isDebugEnabled);
    }

    public void makeResponse(final RoutingContext routingContext,
                             final T response,
                             final Map<String, String> headers,
                             final HttpResponseStatus httpResponseStatus) {
        final HttpServerResponse httpResponse = routingContext.response();

        final String body = httpResponseStatus == HttpResponseStatus.NO_CONTENT ?
                StringUtils.EMPTY :
                adaptBody.apply(response);

        if (!httpResponse.ended() && !httpResponse.closed()) {
            httpResponse.setStatusCode(httpResponseStatus.code());

            final Map<String, String> responseHeaders = new HashMap<>();
            addSafeHeader(CONTENT_TYPE_HEADER, contentTypeValue, responseHeaders);
            responseHeaders.putAll(headers);

            responseHeaders.forEach(httpResponse::putHeader);

            httpResponse.endHandler(event -> {
            }).end(body, encoding);
        }
    }

    private void addSafeHeader(final String key, final String value, final Map<String, String> headers) {
        if ((value != null) && (!value.isEmpty())) {
            headers.put(key, value);
        }
    }

    public abstract void action(final RoutingContext routingContext, final boolean isDebugEnabled);
}
