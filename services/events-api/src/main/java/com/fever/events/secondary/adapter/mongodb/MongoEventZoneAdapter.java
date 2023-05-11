package com.fever.events.secondary.adapter.mongodb;

import com.fever.events.domain.model.EventZone;
import com.fever.mongodb.client.model.MongoEventZone;

import java.util.List;
import java.util.stream.Collectors;

public class MongoEventZoneAdapter {

    private MongoEventZoneAdapter() {
        // do nothing
    }

    public static List<MongoEventZone> adaptMongo(final List<EventZone> domainEventZones) {
        return domainEventZones
                .stream()
                .map(MongoEventZoneAdapter::adapt)
                .collect(Collectors.toList());
    }

    private static MongoEventZone adapt(final EventZone domainEventZone) {
        return MongoEventZone.builder(domainEventZone.getId())
                .name(domainEventZone.getName())
                .price(domainEventZone.getPrice())
                .capacity(domainEventZone.getCapacity())
                .numbered(domainEventZone.getNumbered())
                .build();
    }
}
