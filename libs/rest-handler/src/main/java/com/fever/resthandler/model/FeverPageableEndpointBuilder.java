package com.fever.resthandler.model;

import com.fever.utils.StringUtils;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeverPageableEndpointBuilder<T, R> {
  private HttpMethod httpMethod = HttpMethod.GET;
  private String endpointUrl = StringUtils.EMPTY;
  private Function<Page<R>, Map<String, String>> headers = page -> Map.of(Headers.TOTAL_COUNT_HEADER.getValue(), String.valueOf(page.getTotalSize()));
  private Function<Page<R>, T> adaptFunction = page -> null;
  private Consumer<RoutingContext> validation = routingContext -> {};
  private Function<Page<R>, HttpResponseStatus> httpResponseStatus = page ->
      page.getResultSize() == page.getTotalSize() ?
          HttpResponseStatus.OK:
          HttpResponseStatus.PARTIAL_CONTENT;
  private String contentTypeValue = "application/json";
  private Function<T, String> adaptBody = Json::encode;
  private String encoding = "UTF-8";

  public FeverPageableEndpointBuilder() {
    // do nothing
  }

  public FeverPageableEndpointBuilder<T, R> httpMethod(final HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> endpointUrl(final String endpointUrl) {
    this.endpointUrl = endpointUrl;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> headers(final Function<Page<R>, Map<String, String>> headers) {
    this.headers = headers;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> validation(final Consumer<RoutingContext> validation) {
    this.validation = validation;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> authorization(final Consumer<RoutingContext> authorization) {
    this.validation = authorization;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> adapt(final Function<Page<R>, T> adaptFunction) {
    this.adaptFunction = adaptFunction;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> httpResponseStatus(final Function<Page<R>, HttpResponseStatus> httpResponseStatus) {
    this.httpResponseStatus = httpResponseStatus;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> contentTypeValue(final String contentTypeValue) {
    this.contentTypeValue = contentTypeValue;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> adaptBody(final Function<T, String> adaptBody) {
    this.adaptBody = adaptBody;
    return this;
  }

  public FeverPageableEndpointBuilder<T, R> encoding(final String encoding) {
    this.encoding = encoding;
    return this;
  }

  public FeverPageableEndpoint<T,R> buildWithPageable(final Function<RoutingContext, Single<Page<R>>> action) {
    return new FeverPageableEndpoint<>(this.httpMethod,
        this.endpointUrl,
        this.validation,
        action,
        this.adaptFunction,
        this.headers,
        this.httpResponseStatus,
        this.contentTypeValue,
        this.adaptBody,
        this.encoding);
  }
}
