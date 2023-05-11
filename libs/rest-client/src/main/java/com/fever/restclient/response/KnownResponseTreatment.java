package com.fever.restclient.response;

import com.fever.restclient.model.RequestOptions;
import com.fever.restclient.response.known.DefaultBaseResponseTreatment;
import io.reactivex.Single;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.Map;

public enum KnownResponseTreatment implements BaseResponseTreatment {
  DEFAULT(new DefaultBaseResponseTreatment());

  private final BaseResponseTreatment responseTreatment;

  KnownResponseTreatment(
      BaseResponseTreatmentImpl responseTreatment) {
    this.responseTreatment = responseTreatment;
  }

  public Map<Integer, Rule> getBehaviorRules() {
    return responseTreatment.getBehaviorRules();
  }

  public Single<HttpResponse<Buffer>> apply(final HttpResponse<Buffer> response, final RequestOptions options) {
    return this.responseTreatment.apply(response, options);
  }

  public BaseResponseTreatment withRule(final Integer status, final Rule behaviour) {
    this.responseTreatment.withRule(status, behaviour);
    return this;
  }
}
