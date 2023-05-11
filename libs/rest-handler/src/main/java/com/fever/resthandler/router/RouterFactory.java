package com.fever.resthandler.router;

import com.fever.resthandler.DefaultRestHandler;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

import java.util.stream.Stream;

/**
 * RouterFactory contains a variety of helper methods for building a Vertx HTTP router for a given resource.
 */
public interface RouterFactory {


    /**
     * Given an array of resource names, builds a router with all the REST methods following the REST
     * standard. It also attaches a handler for handling the incoming requests.
     * @param restHandlers
     * @return Router
     */
    default Router restRouter(final Vertx vertx, final DefaultRestHandler... restHandlers) {
        final Router router = Router.router(vertx);
        Stream.of(restHandlers).forEach(restHandler -> restHandler.addHandlersTo(router));
        return router;
    }

}