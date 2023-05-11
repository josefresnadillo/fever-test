package com.fever.events.domain;

import com.fever.events.domain.model.EventsAggregate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventsAggregateBuilderTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenNullId_thenException() {
        assertThrows(AssertionError.class,
                () -> EventsAggregate.builder(null).build());
    }
    @Test
    void givenNullFromDate_thenException() {
        assertThrows(AssertionError.class,
                () -> EventsAggregate.builder(UUID.randomUUID()).from(null).build());
    }

    @Test
    void givenNullToDate_thenException() {
        assertThrows(AssertionError.class,
                () -> EventsAggregate.builder(UUID.randomUUID()).to(null).build());
    }
}
