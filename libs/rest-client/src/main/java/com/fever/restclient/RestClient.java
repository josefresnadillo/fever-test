package com.fever.restclient;

import com.fever.restclient.exceptions.HttpMethodException;
import com.fever.restclient.model.HttpMethod;
import com.fever.restclient.model.RequestOptions;
import com.fever.restclient.response.BaseResponseTreatment;
import io.reactivex.Single;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;

public interface RestClient {

  /**
   * Performs a GET request to the specified url. Then the body is converted to a JsonObject
   *
   * @param options   Options including common elements for requests
   * @param treatment The strategy to treat the response based on error codes or whatever
   * @return The response received to the request, parsed by the strategy and encapsulated in a {@code
   * Single<JsonObject>}
   */
  default Single<HttpResponse<Buffer>> getBodyFromRequest(final RequestOptions options, final BaseResponseTreatment treatment) {
    try {
      return makeRequest(HttpMethod.GET, options, treatment);
    } catch (HttpMethodException e) {
      return Single.error(e);
    }
  }

  Single<HttpResponse<Buffer>> makeRequest(final HttpMethod method, final RequestOptions options, final BaseResponseTreatment treatment);
}
