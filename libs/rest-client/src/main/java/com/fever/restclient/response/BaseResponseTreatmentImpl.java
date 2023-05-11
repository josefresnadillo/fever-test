package com.fever.restclient.response;

import com.fever.restclient.model.RequestOptions;
import io.reactivex.Single;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class BaseResponseTreatmentImpl implements BaseResponseTreatment {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponseTreatmentImpl.class.getName());

  protected static final int DEFAULT_BEHAVIOR = 0;

  private final HashMap<Integer, Rule> behaviorRules = new HashMap<>();


  public BaseResponseTreatmentImpl() {
    // do nothing
  }

  public Single<HttpResponse<Buffer>> apply(final HttpResponse<Buffer> response,
                                            final RequestOptions externalRequestOptions) {
    if (behaviorRules.containsKey(response.statusCode())) {
      return behaviorRules.get(response.statusCode()).apply(response, externalRequestOptions);
    }
    return behaviorRules.get(DEFAULT_BEHAVIOR).apply(response, externalRequestOptions);
  }

  public Map<Integer, Rule> getBehaviorRules() {
    return behaviorRules;
  }

  public BaseResponseTreatment withRule(Integer status, Rule behaviour) {
    this.behaviorRules.remove(status);
    this.behaviorRules.put(status, behaviour);
    return this;
  }

  protected static void logResponseError(HttpResponse<Buffer> response, RequestOptions options) {
    LOGGER.error(response.bodyAsString());
  }

  protected static void logResponseInfo(HttpResponse<Buffer> response, RequestOptions options) {
    LOGGER.info(new JsonObject(options.getQueryParams()).encode());
  }
}
