package com.fever.resthandler.router;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RouterBuilder is a fluent builder class that helps you to build a Vertx router with contexts and subrouters.
 */
public class RouterBuilder implements RouterFactory {

  private final Vertx vertx;
  private String context;
  private final Map<String, List<Router>> subRouters;

  public RouterBuilder(Vertx vertx) {
    this.vertx = vertx;
    subRouters = new HashMap<>();
  }

  /**
   * Puts a context to the different routes of the router.
   *
   * @param context
   * @return
   */
  public RouterBuilder withContext(String context) {
    this.context = context;
    return this;
  }

  /**
   * Attaches a subrouter in the given mount point.
   *
   * @param mountPoint
   * @param subRouter
   * @return
   */
  public RouterBuilder withSubRouter(final String mountPoint, final Router subRouter) {
    subRouters.computeIfAbsent(mountPoint, k -> new ArrayList<>());
    subRouters.get(mountPoint).add(subRouter);
    return this;
  }

  /**
   * Finally builds and returns the constructed router.
   *
   * @return
   */
  public Router build() {
    var router = Router.router(vertx);
    if (context != null) {
      router.mountSubRouter("/" + context, router);
    } else {
      subRouters.entrySet().stream()
          .flatMap(entry ->
              entry.getValue().stream().map(route -> Map.entry(entry.getKey(), route))
          ).forEach(route ->
          router.mountSubRouter(route.getKey(), route.getValue())
      );
    }
    return router;
  }
}
