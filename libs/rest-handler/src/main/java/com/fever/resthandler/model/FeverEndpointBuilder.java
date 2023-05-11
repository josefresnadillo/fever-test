package com.fever.resthandler.model;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.function.Consumer;
import java.util.function.Function;

public class FeverEndpointBuilder<T> {
    private HttpMethod httpMethod = HttpMethod.GET;
    private String endpointUrl = "";
    private Consumer<RoutingContext> validation = routingContext -> {
    };
    private HttpResponseStatus responseStatus = HttpResponseStatus.OK;
    private String contentTypeValue = "application/json";
    private Function<T, String> adaptBody = Json::encode;
    private String encoding = "UTF-8";

    public FeverEndpointBuilder() {
        // do nothing
    }

    public FeverEndpointBuilder<T> httpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public FeverEndpointBuilder<T> endpointUrl(final String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public FeverEndpointBuilder<T> validation(final Consumer<RoutingContext> validation) {
        this.validation = validation;
        return this;
    }

    public FeverEndpointBuilder<T> responseStatus(final HttpResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public FeverEndpointBuilder<T> contentTypeValue(final String contentTypeValue) {
        this.contentTypeValue = contentTypeValue;
        return this;
    }

    public FeverEndpointBuilder<T> adaptBody(final Function<T, String> adaptBody) {
        this.adaptBody = adaptBody;
        return this;
    }

    public FeverEndpointBuilder<T> encoding(final String encoding) {
        this.encoding = encoding;
        return this;
    }

    public FeverSingleEndpoint<T> buildWithSingle(final Function<RoutingContext, Single<T>> action) {
        return new FeverSingleEndpoint<>(
                this.httpMethod,
                this.endpointUrl,
                this.validation,
                action,
                this.responseStatus,
                this.contentTypeValue,
                this.adaptBody,
                this.encoding);
    }
}
