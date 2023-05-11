package com.fever.events.primary.handler;

import com.fever.events.primary.adapter.SearchEvents400ResponseAdapter;
import com.fever.exceptions.domain.model.FeverErrorType;
import com.fever.exceptions.exception.BackendException;
import com.fever.exceptions.exception.UnexpectedException;
import io.vertx.core.Handler;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.Json;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Arrays;

public class DefaultErrorHandler implements Handler<RoutingContext> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorHandler.class.getName());

    public DefaultErrorHandler() {
        // do nothing
    }

    @Override
    public void handle(RoutingContext routingContext) {
        final Throwable error = routingContext.failure();

        final String body = error instanceof BackendException ?
                handleFeverException(routingContext, (BackendException) error) :
                handleUnknownException(routingContext, error);

        LOGGER.error(String.format("Exception thrown %s cause - StackTrace: %s",
                error.getMessage(),
                Arrays.toString(error.getStackTrace())));

        routingContext.response()
                .putHeader("Content-Type", "application/json")
                .end(body);
    }

    private String handleFeverException(final RoutingContext routingContext,
                                        final BackendException backendException) {
        routingContext.response().setStatusCode(backendException.getHttpCode());
        return Json.encodePrettily(SearchEvents400ResponseAdapter.adapt(backendException));
    }

    private String handleUnknownException(final RoutingContext routingContext,
                                          final Throwable error) {
        var response = new UnexpectedException(FeverErrorType.INTERNAL_SERVER_ERROR, error.getMessage());
        routingContext.response().setStatusCode(response.getHttpCode());
        return Json.encodePrettily(response.getErrorMessageList());
    }
}
