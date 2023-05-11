package com.fever.events.domain.model;

import com.fever.utils.ListUtils;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * I have named this class EventsAggregate (that doesn't sound very ubiquitous) just for the shake of this test
 * A better name would be EventsSummary or something like that
 */
public class EventsAggregate {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsAggregate.class.getName());

    private static final String NEW_EVENT_LOG = "New Event or event has change: %s";

    private final UUID id;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final Map<String, Event> events;

    public EventsAggregate(final Builder builder) {
        this.id = builder.id;
        this.from = builder.from;
        this.to = builder.to;
        this.events = new HashMap<>();
        consolidateEvents(builder.oldEvents, builder.newEvents);
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events.values());
    }

    // Some little behaviour
    public List<Event> getEventsInRange(final LocalDateTime filterStartDate,
                                        final LocalDateTime filterEndDate) {
        return getEvents()
                .stream()
                .filter(event -> event.getFrom().isAfter(filterStartDate)
                        && event.getTo().isBefore(filterEndDate))
                .collect(Collectors.toList());
    }

    public List<Event> getNewEvents() {
        return getEvents()
                .stream()
                .filter(Event::getIsNewEvent)
                .collect(Collectors.toList());
    }

    private void consolidateEvents(final List<Event> oldEvents,
                                   final List<Event> newEvents) {
        // Put old events first, so we can dismiss new events if they are already in the database
        oldEvents.forEach(event -> this.events.put(event.getId(), event));
        // Add or dismiss new events if they are already in the map
        newEvents.forEach(newEvent -> {
            final Event oldEvent = this.events.get(newEvent.getId());
            if (eventIsNewOrHasChange(newEvent, oldEvent)) {
                LOGGER.info(String.format(NEW_EVENT_LOG, newEvent));
                this.events.put(newEvent.getId(), newEvent); // add it or replace it
            }
        });
    }

    private boolean eventIsNewOrHasChange(final Event newEvent, final Event oldEvent) {
        // A new event only replace an old event if it is brand new or different
        // Doing so we have to store less events into mongodb
        return !newEvent.equals(oldEvent);
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        EventsAggregate that = (EventsAggregate) object;
        return java.util.Objects.equals(id, that.id) && java.util.Objects.equals(from, that.from) && java.util.Objects.equals(to, that.to) && java.util.Objects.equals(events, that.events);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id, from, to, events);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "EventsSummary{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", events=" + events +
                '}';
    }

    public static Builder builder(final UUID id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final UUID id;
        private LocalDateTime from = LocalDateTime.MIN;
        private LocalDateTime to = LocalDateTime.MAX;
        private final List<Event> oldEvents;
        private final List<Event> newEvents;

        public Builder(final UUID id) {
            assert id != null;
            this.id = id;
            this.oldEvents = new ArrayList<>();
            this.newEvents = new ArrayList<>();
        }

        public Builder from(final LocalDateTime from) {
            assert from != null;
            this.from = from;
            return this;
        }

        public Builder to(final LocalDateTime to) {
            assert to != null;
            this.to = to;
            return this;
        }

        public Builder oldEvents(final List<Event> events) {
            this.oldEvents.addAll(ListUtils.listOrEmpty(events));
            return this;
        }

        public Builder newEvents(final List<Event> events) {
            this.newEvents.addAll(ListUtils.listOrEmpty(events));
            return this;
        }

        public EventsAggregate build() {
            return new EventsAggregate(this);
        }
    }
}