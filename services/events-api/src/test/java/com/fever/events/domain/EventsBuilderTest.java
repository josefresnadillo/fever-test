package com.fever.events.domain;

import com.fever.events.domain.model.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventsBuilderTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenNullId_thenException() {
        assertThrows(AssertionError.class,
                () -> Event.builder(null).build());
    }

    @Test
    void givenNullFromDate_thenException() {
        assertThrows(AssertionError.class,
                () -> Event.builder(UUID.randomUUID().toString()).from(null).build());
    }

    @Test
    void givenNullTitle_thenException() {
        assertThrows(AssertionError.class,
                () -> Event.builder(UUID.randomUUID().toString()).title(null).build());
    }

    @Test
    void givenEmptyTitle_thenException() {
        assertThrows(AssertionError.class,
                () -> Event.builder(UUID.randomUUID().toString()).title("").build());
    }

    @Test
    void givenNullToDate_thenException() {
        assertThrows(AssertionError.class,
                () -> Event.builder(UUID.randomUUID().toString()).to(null).build());
    }

    @Test
    void givenNullZones_thenException() {
        assertThrows(AssertionError.class,
                () -> Event.builder(UUID.randomUUID().toString()).zones(null).build());
    }
}
