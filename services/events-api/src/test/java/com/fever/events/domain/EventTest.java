package com.fever.events.domain;

import com.fever.events.domain.model.Event;
import com.fever.events.mother.DomainMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenEvent_whenOnlyOneZone_thenMinimunAndMaximunAreEqualsPrice() {
        final Event event = DomainMother.simpleNewEvent();
        assertEquals(DomainMother.ZONE_1_PRICE, event.getMinPrice());
        assertEquals(DomainMother.ZONE_1_PRICE, event.getMaxPrice());
    }

    @Test
    void givenEvent_whenHasSeveralZones_thenMaxAndMinAreNotEquals() {
        final Event event = DomainMother.simpleNewEventWithTwoZones();
        assertEquals(DomainMother.ZONE_1_PRICE, event.getMinPrice());
        assertEquals(DomainMother.ZONE_2_PRICE, event.getMaxPrice());
    }
}
