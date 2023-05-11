package com.fever.events.primary.adapter;

import com.fever.events.domain.model.Event;
import com.fever.events.primary.api.EventList;
import com.fever.events.primary.api.EventSummary;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventListAdapter {
    private EventListAdapter() {
        // do nothing
    }

    public static EventList adapt(final List<Event> domainEvents) {
        return new EventList()
                .events(domainEvents.stream()
                        .map(EventListAdapter::adapt)
                        .collect(Collectors.toList()));
    }

    private static EventSummary adapt(final Event domainEvent) {
        return new EventSummary()
                .id(UUID.nameUUIDFromBytes(domainEvent.getId().getBytes()))
                .title(domainEvent.getTitle())
                .startDate(domainEvent.getFrom().toLocalDate())
                .endDate(domainEvent.getTo().toLocalDate())
                .startTime(domainEvent.getFrom().toLocalTime().toString())
                .endTime(domainEvent.getTo().toLocalTime().toString())
                .minPrice(domainEvent.getMinPrice())
                .maxPrice(domainEvent.getMaxPrice());
    }
}
