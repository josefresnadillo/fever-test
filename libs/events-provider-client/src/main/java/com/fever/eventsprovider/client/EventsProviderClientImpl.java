package com.fever.eventsprovider.client;

import com.fever.cache.FeverCache;
import com.fever.eventsprovider.client.config.EventsProviderConfig;
import com.fever.eventsprovider.client.model.ProviderEventList;
import com.fever.restclient.RestClient;
import com.fever.restclient.model.RequestOptions;
import io.reactivex.Single;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class EventsProviderClientImpl extends EventsProviderClientBase implements EventsProviderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsProviderClientImpl.class.getName());

    public EventsProviderClientImpl(final RestClient restClient,
                                    final FeverCache cache,
                                    final EventsProviderConfig config) {
        super(restClient, cache, config);
    }

    // GET /api/events
    @Override
    public Single<ProviderEventList> listEvents() {
        final RequestOptions options = getConfig().listEventsConfig();
        final Single<ProviderEventList> result = getBodyFromRequest(options, ProviderEventList.class)
                .onErrorReturn((throwable) -> {
                    LOGGER.error("Error calling to the events provider", throwable);
                    return new ProviderEventList(); // it should never fail
                });
        LOGGER.info("Calling to the events provider was successful");
        return result;
    }
}
