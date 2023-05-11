package com.fever.events.mother;

import com.fever.events.domain.model.Event;
import com.fever.events.domain.model.EventZone;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DomainMother {

    public static final LocalDateTime startAt = LocalDateTime.of(2023, 1, 1, 10, 0, 0,0);
    public static final LocalDateTime endsAt = LocalDateTime.of(2023, 1, 1, 11, 0, 0,0);

    public static final String NEW_EVENT_ID_1 = "Event1";
    public static final String OLD_EVENT_ID_1 = "oldEvent1";

    public static final BigDecimal ZONE_1_PRICE = BigDecimal.valueOf(10);
    public static final BigDecimal ZONE_2_PRICE = BigDecimal.valueOf(20);

    public static Event simpleNewEvent() {
        return Event.builder(NEW_EVENT_ID_1)
                .from(startAt)
                .to(endsAt)
                .title("New Event 1")
                .newEvent(true)
                .zones(List.of(simpleEventZone1()))
                .build();
    }

    public static Event simpleOldEvent() {
        return Event.builder(OLD_EVENT_ID_1)
                .from(startAt)
                .to(endsAt)
                .title("Old Event 1")
                .newEvent(false)
                .zones(List.of(simpleEventZone1()))
                .build();
    }

    public static List<Event> newEventEqualsToOldEvent(){
        final Event newEvent = Event.builder(NEW_EVENT_ID_1)
                .from(startAt)
                .to(endsAt)
                .title("New Event 1")
                .newEvent(true)
                .zones(List.of(simpleEventZone1()))
                .build();

        final Event oldEvent = Event.builder(NEW_EVENT_ID_1)
                .from(startAt)
                .to(endsAt)
                .title("New Event 1")
                .newEvent(false)
                .zones(List.of(simpleEventZone1()))
                .build();

        return List.of(newEvent, oldEvent);
    }

    public static Event simpleNewEventWithTwoZones() {
        return Event.builder(NEW_EVENT_ID_1)
                .from(startAt)
                .to(endsAt)
                .title("New Event 1")
                .newEvent(true)
                .zones(List.of(simpleEventZone1(), simpleEventZone2()))
                .build();
    }

    public static EventZone simpleEventZone1(){
        return EventZone.builder("zone1")
                .numbered(true)
                .capacity(10)
                .price(ZONE_1_PRICE)
                .name("Zone expensive")
                .build();
    }

    public static EventZone simpleEventZone1WithDifferentCapacity(){
        return EventZone.builder("zone1")
                .numbered(true)
                .capacity(2)
                .price(ZONE_1_PRICE)
                .name("Zone expensive")
                .build();
    }

    public static EventZone simpleEventZone2(){
        return EventZone.builder("zone2")
                .numbered(true)
                .capacity(10)
                .price(ZONE_2_PRICE)
                .name("Zone expensive")
                .build();
    }

    public static EventZone simpleEventZone2WithDifferentCapacity(){
        return EventZone.builder("zone2")
                .numbered(true)
                .capacity(10)
                .price(ZONE_2_PRICE)
                .name("Zone expensive")
                .build();
    }
}
