package com.fever.restclient;

import com.fever.restclient.model.HttpMethod;
import com.fever.restclient.model.RequestOptions;
import com.fever.restclient.response.BaseResponseTreatment;
import io.reactivex.Single;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

public class RestClientImpl implements RestClient {

  private final WebClient webClient;

  public RestClientImpl(final WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Single<HttpResponse<Buffer>> makeRequest(final HttpMethod method,
                                                  final RequestOptions options,
                                                  final BaseResponseTreatment treatment) {
    Single<HttpResponse<Buffer>> rxSend = method.doRequest(this.webClient, options);
    if (options.getRetries() > 0) {
      rxSend = rxSend.retry(options.getRetries());
    }
    return rxSend.flatMap(bufferHttpResponse -> treatment.apply(bufferHttpResponse, options));
  }
}
