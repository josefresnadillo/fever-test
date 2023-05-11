package com.fever.events.secondary.adapter.domain;

import com.fever.events.domain.model.Event;
import com.fever.eventsprovider.client.model.ProviderBaseEvent;
import com.fever.eventsprovider.client.model.ProviderEventList;
import com.fever.mongodb.client.model.MongoEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventAdapter {

    private EventAdapter() {
        // do nothing
    }

    public static List<Event> adaptNew(final ProviderEventList providerEventList) {
        return providerEventList.getEvents()
                .stream()
                .map(EventAdapter::adaptNew)
                .collect(Collectors.toList());
    }

    public static List<Event> adaptOld(final List<MongoEvent> mongoEvents) {
        return mongoEvents
                .stream()
                .map(EventAdapter::adaptOld)
                .collect(Collectors.toList());
    }

    private static Event adaptNew(final ProviderBaseEvent providerEvent) {
        return Event.builder(providerEvent.getId())
                .title(providerEvent.getTitle())
                .from(providerEvent.getEvent().getStartDate())
                .to(providerEvent.getEvent().getEndDate())
                .zones(EventZoneAdapter.adaptProvider(providerEvent.getEvent().getZones()))
                .newEvent(true)
                .build();
    }

    private static Event adaptOld(final MongoEvent mongoEvent) {
        return Event.builder(mongoEvent.getId())
                .title(mongoEvent.getTitle())
                .from(LocalDateTime.parse(mongoEvent.getFrom()))
                .to(LocalDateTime.parse(mongoEvent.getTo()))
                .zones(EventZoneAdapter.adaptMongo(mongoEvent.getZones()))
                .newEvent(false)
                .build();
    }
}
