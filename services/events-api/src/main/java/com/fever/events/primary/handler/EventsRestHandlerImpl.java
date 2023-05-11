package com.fever.events.primary.handler;

import com.fever.events.config.Config;
import com.fever.events.primary.api.SearchEvents200Response;
import com.fever.events.primary.api.SearchEvents400ResponseError;
import com.fever.events.primary.model.EventsSearch;
import com.fever.events.primary.service.EventsService;
import com.fever.events.primary.service.RateLimiter;
import com.fever.events.primary.validation.EventsSearchValidation;
import com.fever.resthandler.model.FeverEndpoint;
import com.fever.resthandler.model.FeverEndpointBuilder;
import com.fever.utils.StringUtils;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

import javax.inject.Inject;

public class EventsRestHandlerImpl implements EventsRestHandler {

    private final EventsService eventsService;
    private final RateLimiter rateLimiter;

    private final FeverEndpoint<SearchEvents200Response> eventsByCriteriaEndpoint;

    @Inject
    public EventsRestHandlerImpl(final EventsService eventsService,
                                 final RateLimiter rateLimiter) {
        this.eventsService = eventsService;
        this.rateLimiter = rateLimiter;
        this.eventsByCriteriaEndpoint = new FeverEndpointBuilder<SearchEvents200Response>()
                .httpMethod(HttpMethod.GET)
                .endpointUrl(Config.getInstance().getEventsUrl())
                .validation(EventsSearchValidation::validate)
                .buildWithSingle(this::listEventsByCriteria);
    }

    @Override
    public void addHandlersTo(Router router) {
        addHandlerTo(router,
                eventsByCriteriaEndpoint.getHttpMethod(),
                eventsByCriteriaEndpoint.getEndpointUrl(),
                routingContext -> eventsByCriteriaEndpoint.performAction(routingContext, Config.getInstance().isDebugEnabled()),
                new DefaultErrorHandler());
    }

    @Override
    public Single<SearchEvents200Response> listEventsByCriteria(RoutingContext routingContext) {
        final EventsSearch search = new EventsSearch(routingContext.queryParams());
        rateLimiter.checkThrottling(routingContext, "listEventsByCriteria");
        return eventsService.listByCriteria(search)
                .map(eventList -> new SearchEvents200Response()
                        .error(new SearchEvents400ResponseError()
                                .code(StringUtils.EMPTY)
                                .message(StringUtils.EMPTY))
                        .data(eventList));
    }
}
