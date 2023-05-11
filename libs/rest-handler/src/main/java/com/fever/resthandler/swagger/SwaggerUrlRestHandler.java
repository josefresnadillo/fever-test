package com.fever.resthandler.swagger;

import com.fever.resthandler.DefaultRestHandler;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public interface SwaggerUrlRestHandler extends DefaultRestHandler {

  Single<JsonObject> getSwaggerUrl(RoutingContext routingContext);
}
