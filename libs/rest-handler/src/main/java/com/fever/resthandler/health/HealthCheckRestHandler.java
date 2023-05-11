package com.fever.resthandler.health;

import com.fever.resthandler.DefaultRestHandler;
import io.reactivex.Single;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * HealthCheckRestHandler represent a /health rest handler that all microservice must implements
 */
public interface HealthCheckRestHandler extends DefaultRestHandler {

  /**
   * check will check your microservice status (database, third party endpoints...etc).
   * HTTP 200 means OK. HTTP 503 means KO.
   */
  Single<HealthStatusDto> check(RoutingContext routingContext);
}
