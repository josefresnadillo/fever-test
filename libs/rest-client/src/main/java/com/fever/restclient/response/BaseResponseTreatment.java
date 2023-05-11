package com.fever.restclient.response;

import com.fever.restclient.model.RequestOptions;
import io.reactivex.Single;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.Map;

public interface BaseResponseTreatment {

  Single<HttpResponse<Buffer>> apply(final HttpResponse<Buffer> response, final RequestOptions externalRequestOptions);

  Map<Integer, Rule> getBehaviorRules();

  BaseResponseTreatment withRule(final Integer status, final Rule behaviour);
}
