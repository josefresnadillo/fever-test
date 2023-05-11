package com.fever.events.verticles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fever.events.config.Config;
import com.fever.events.module.DaggerHandlerComponents;
import com.fever.resthandler.router.RouterBuilder;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.CorsHandler;

public class MainVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class.getName());

    @Override
    public void start() {

        var handlerComponents = DaggerHandlerComponents.create();

        var sharedConfig = Config.getInstance();

        var httpHost = sharedConfig.getHost().orElse(Config.DEFAULT_HOST);
        var httpPort = sharedConfig.getPort().orElse(Config.DEFAULT_PORT);

        // Handlers
        var healthHandler = handlerComponents.buildHealthCheckRestHandler();
        var swaggerHandler = handlerComponents.buildDefaultSwaggerRestHandler();
        var eventsRestHandler = handlerComponents.buildEventsRestHandlerImpl();

        var routerBuilder = new RouterBuilder(vertx);
        var baseRouter = Router.router(vertx);

        // Controllers
        var healthController = routerBuilder.restRouter(vertx, healthHandler);
        var swaggerController = routerBuilder.restRouter(vertx, swaggerHandler);
        var eventsController = routerBuilder.restRouter(vertx, eventsRestHandler);

        // Vertx routers

        baseRouter.route().handler(CorsHandler.create("*"));
        baseRouter.mountSubRouter("/", healthController);
        baseRouter.mountSubRouter("/", swaggerController);
        baseRouter.mountSubRouter("/", eventsController);

        // Json
        DatabindCodec.prettyMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        vertx.createHttpServer().requestHandler(baseRouter).rxListen(httpPort, httpHost)
                .subscribe(httpServer -> LOGGER.info(String.format("HTTP server started on http://%s:%s",
                        httpHost, httpPort)));
    }
}
