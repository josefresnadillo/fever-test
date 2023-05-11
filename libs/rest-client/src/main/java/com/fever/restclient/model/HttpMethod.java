package com.fever.restclient.model;

import com.fever.restclient.exceptions.HttpMethodException;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpRequest;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

import javax.validation.constraints.NotNull;

public enum HttpMethod {
  GET(WebClient::getAbs, false),
  POST(WebClient::postAbs, true),
  PUT(WebClient::putAbs, true),
  PATCH(WebClient::patchAbs, true),
  DELETE(WebClient::deleteAbs, true),
  HEAD(WebClient::headAbs, false);

  private final BiFunction<WebClient, String, HttpRequest<Buffer>> function;
  private final boolean canHavePayload;

  HttpMethod(final BiFunction<WebClient, String, HttpRequest<Buffer>> function,
             final boolean canHavePayload) {
    this.function = function;
    this.canHavePayload = canHavePayload;
  }

  public Single<HttpResponse<Buffer>> doRequest(@NotNull final WebClient client,
                                                @NotNull final RequestOptions externalRequestOptions)  {
    try {
      final HttpRequest<Buffer> bufferHttpRequest = function.apply(client, externalRequestOptions.getUrl());

      externalRequestOptions.getHeaders().forEach(bufferHttpRequest::putHeader);
      externalRequestOptions.getQueryParams().forEach((key, value) -> bufferHttpRequest.addQueryParam(key, (String) value));

      if (externalRequestOptions.getFormPayload() != null) {
        return bufferHttpRequest.rxSendForm((MultiMap) externalRequestOptions.getFormPayload());
      }

      if (!canHavePayload || externalRequestOptions.getPayload() == null) {
        return bufferHttpRequest.rxSend();
      } else {
        return bufferHttpRequest.rxSendJson(externalRequestOptions.getPayload());
      }

    } catch (Exception e) {
      throw new HttpMethodException(e);
    }
  }
}
