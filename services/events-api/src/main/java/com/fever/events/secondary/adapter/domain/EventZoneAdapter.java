package com.fever.events.secondary.adapter.domain;

import com.fever.events.domain.model.EventZone;
import com.fever.eventsprovider.client.model.ProviderEventZone;
import com.fever.mongodb.client.model.MongoEventZone;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class EventZoneAdapter {

    private EventZoneAdapter() {
        // do nothing
    }

    public static List<EventZone> adaptProvider(final List<ProviderEventZone> providerEventZones) {
        return providerEventZones
                .stream()
                .map(EventZoneAdapter::adapt)
                .collect(Collectors.toList());
    }

    public static List<EventZone> adaptMongo(final List<MongoEventZone> mongoEventZones) {
        return mongoEventZones
                .stream()
                .map(EventZoneAdapter::adapt)
                .collect(Collectors.toList());
    }

    private static EventZone adapt(final ProviderEventZone providerEventZone) {
        return EventZone.builder(providerEventZone.getId())
                .name(providerEventZone.getName())
                .price(BigDecimal.valueOf(providerEventZone.getPrice()))
                .capacity(providerEventZone.getCapacity())
                .numbered(providerEventZone.getNumbered())
                .build();
    }

    private static EventZone adapt(final MongoEventZone mongoEventZone) {
        return EventZone.builder(mongoEventZone.getId())
                .name(mongoEventZone.getName())
                .price(mongoEventZone.getPrice())
                .capacity(mongoEventZone.getCapacity())
                .numbered(mongoEventZone.getNumbered())
                .build();
    }
}
