package com.fever.resthandler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.ext.web.Route;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.ResponseTimeHandler;

import static io.vertx.core.http.HttpMethod.*;

public interface DefaultRestHandler {

    void addHandlersTo(Router router);

    default void addHandlerTo(final Router router,
                              final HttpMethod httpMethod,
                              final String path,
                              final Handler<RoutingContext> handlerMethod,
                              final Handler<RoutingContext> errorHandler) {
        final Route route = generateRoute(router, httpMethod, path);
        final Logger logger = LoggerFactory.getLogger(DefaultRestHandler.class.getName());
        route.handler(ResponseTimeHandler.create())
                .handler(BodyHandler.create())
                .handler(handlerMethod)
                .failureHandler(errorHandler);
        logger.info(String.format("%s %s handler created", httpMethod, path));
    }

    private Route generateRoute(final Router router, final HttpMethod method, final String path) {
        if (GET.equals(method)) {
            return router.get(path);
        }
        if (PUT.equals(method)) {
            return router.put(path);
        }
        if (POST.equals(method)) {
            return router.post(path);
        }
        if (PATCH.equals(method)) {
            return router.patch(path);
        }
        if (DELETE.equals(method)) {
            return router.delete(path);
        }
        if (CONNECT.equals(method)) {
            return router.connect(path);
        }
        if (TRACE.equals(method)) {
            return router.trace(path);
        }
        if (OPTIONS.equals(method)) {
            return router.options(path);
        }
        if (HEAD.equals(method)) {
            return router.head(path);
        }
        throw new IllegalArgumentException("Method not supported");
    }
}
