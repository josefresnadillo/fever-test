package com.fever.events.secondary.dao.eventsprovider;

import com.fever.eventsprovider.client.EventsProviderClient;
import com.fever.eventsprovider.client.model.ProviderEventList;
import io.reactivex.Single;

public class EventsProviderDao {
    private final EventsProviderClient eventsProviderClient;

    public EventsProviderDao(final EventsProviderClient eventsProviderClient) {
        this.eventsProviderClient = eventsProviderClient;
    }
    public Single<ProviderEventList> listByCriteria() {
        return eventsProviderClient.listEvents();
    }
}
