package com.fever.events.domain;

import com.fever.events.domain.model.Event;
import com.fever.events.domain.model.EventsAggregate;
import com.fever.events.mother.DomainMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsAggregateTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenNewAndOldEvents_whenGetNewEvents_thenReturnOnlyNew() {

        final EventsAggregate aggregate = EventsAggregate.builder(UUID.randomUUID())
                .from(DomainMother.startAt)
                .to(DomainMother.endsAt)
                .oldEvents(List.of(DomainMother.simpleOldEvent()))
                .newEvents(List.of(DomainMother.simpleNewEvent()))
                .build();

        final List<Event> newEvents = aggregate.getNewEvents();

        assertEquals(1, newEvents.size());
        assertTrue(aggregate.getEvents()
                .stream()
                .anyMatch(Event::getIsNewEvent));
    }

    @Test
    void givenOnlyOldEvents_whenGetNewEvents_thenReturnEmpty() {

        final EventsAggregate aggregate = EventsAggregate.builder(UUID.randomUUID())
                .from(DomainMother.startAt)
                .to(DomainMother.endsAt)
                .oldEvents(List.of(DomainMother.simpleOldEvent()))
                .build();

        final List<Event> newEvents = aggregate.getNewEvents();

        assertEquals(0, newEvents.size());
    }

    @Test
    void givenNewAndOldEvents_whenBothInRange_thenReturnAll() {

        final LocalDateTime startAt = DomainMother.startAt.minusDays(1);
        final LocalDateTime endsAt = DomainMother.endsAt.plusDays(1);
        final EventsAggregate aggregate = EventsAggregate.builder(UUID.randomUUID())
                .from(DomainMother.startAt)
                .to(DomainMother.endsAt)
                .oldEvents(List.of(DomainMother.simpleOldEvent()))
                .newEvents(List.of(DomainMother.simpleNewEvent()))
                .build();

        final List<Event> newEvents = aggregate.getEventsInRange(startAt, endsAt);

        assertEquals(2, newEvents.size());
    }

    @Test
    void givenNewAndOldEvents_whenAnyInRange_thenReturnEmpty() {

        final LocalDateTime startAt = DomainMother.startAt.plusDays(1);
        final LocalDateTime endsAt = DomainMother.endsAt.minusDays(1);
        final EventsAggregate aggregate = EventsAggregate.builder(UUID.randomUUID())
                .from(DomainMother.startAt)
                .to(DomainMother.endsAt)
                .oldEvents(List.of(DomainMother.simpleOldEvent()))
                .newEvents(List.of(DomainMother.simpleNewEvent()))
                .build();

        final List<Event> newEvents = aggregate.getEventsInRange(startAt, endsAt);

        assertEquals(0, newEvents.size());
    }

    @Test
    void givenEvents_whenHasDifferentNewAndOldEvents_thenReturnAll() {

        final EventsAggregate aggregate = EventsAggregate.builder(UUID.randomUUID())
                .from(DomainMother.startAt)
                .to(DomainMother.endsAt)
                .oldEvents(List.of(DomainMother.simpleOldEvent()))
                .newEvents(List.of(DomainMother.simpleNewEvent()))
                .build();

        assertEquals(2, aggregate.getEvents().size());
        assertTrue(aggregate.getEvents()
                .stream()
                .anyMatch(event -> event.getId().equals(DomainMother.NEW_EVENT_ID_1)));
        assertTrue(aggregate.getEvents()
                .stream()
                .anyMatch(event -> event.getId().equals(DomainMother.OLD_EVENT_ID_1)));
    }

    @Test
    void givenEvents_whenNewAndOldEventsAreTheSame_thenReturnOldEvent() {

        final EventsAggregate aggregate = EventsAggregate.builder(UUID.randomUUID())
                .from(DomainMother.startAt)
                .to(DomainMother.endsAt)
                .oldEvents(DomainMother.newEventEqualsToOldEvent())
                .build();

        assertEquals(1, aggregate.getEvents().size());
        assertTrue(aggregate.getEvents()
                .stream()
                .anyMatch(event -> !event.getIsNewEvent()));
    }
}
