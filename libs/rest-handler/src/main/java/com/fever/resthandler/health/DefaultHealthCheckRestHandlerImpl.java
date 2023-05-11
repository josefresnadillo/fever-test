package com.fever.resthandler.health;

import com.fever.resthandler.model.FeverEndpoint;
import com.fever.resthandler.model.FeverEndpointBuilder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Map;

/**
 * DefaultHealthCheckRestHandlerImpl is a default implementation of /health endpoint. You should implements your own
 * ones.
 */
public class DefaultHealthCheckRestHandlerImpl implements HealthCheckRestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHealthCheckRestHandlerImpl.class.getName());

  public static final String RESOURCE_NAME = "/health";

  private final FeverEndpoint<HealthStatusDto> healthEndpoint;

  public DefaultHealthCheckRestHandlerImpl() {
    this.healthEndpoint = new FeverEndpointBuilder<HealthStatusDto>()
        .httpMethod(HttpMethod.GET)
        .endpointUrl(RESOURCE_NAME)
        .buildWithSingle(this::check);
  }

  /**
   * addHandlers add a rest endpoint to an existing Router
   */
  @Override
  public void addHandlersTo(Router router) {
    router.get(RESOURCE_NAME).handler(this::check);
    LOGGER.info(String.format("GET %s handler created", RESOURCE_NAME));
  }

  /**
   * check will check your microservice status (database, third party endpoints...etc). HTTP 200 means OK. HTTP 503
   * means KO.
   */
  @Override
  public Single<HealthStatusDto> check(RoutingContext routingContext) {
    final HealthStatusDto response = new HealthStatusDto();
    this.healthEndpoint.makeResponse(routingContext,
        response,
        Map.of(),
        HttpResponseStatus.OK);
    return Single.just(response);
  }
}
