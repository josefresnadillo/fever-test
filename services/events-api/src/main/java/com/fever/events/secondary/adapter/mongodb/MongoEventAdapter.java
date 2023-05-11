package com.fever.events.secondary.adapter.mongodb;

import com.fever.events.domain.model.Event;
import com.fever.mongodb.client.model.MongoEvent;

import java.util.List;
import java.util.stream.Collectors;

public class MongoEventAdapter {

    private MongoEventAdapter() {
        // do nothing
    }

    public static List<MongoEvent> adapt(final List<Event> domainEvents) {
        return domainEvents
                .stream()
                .map(MongoEventAdapter::adapt)
                .collect(Collectors.toList());
    }

    private static MongoEvent adapt(final Event mongoEvent) {
        return MongoEvent.builder(mongoEvent.getId())
                .title(mongoEvent.getTitle())
                .from(mongoEvent.getFrom().toString())
                .to(mongoEvent.getTo().toString())
                .zones(MongoEventZoneAdapter.adaptMongo(mongoEvent.getZones()))
                .newEvent(false)
                .build();
    }
}
