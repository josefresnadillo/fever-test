package com.fever.events.domain;

import com.fever.events.domain.model.EventZone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventZoneBuilderTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenNullId_thenException() {
        assertThrows(AssertionError.class,
                () -> EventZone.builder(null).build());
    }

    @Test
    void givenNullName_thenException() {
        assertThrows(AssertionError.class,
                () -> EventZone.builder(UUID.randomUUID().toString()).name(null).build());
    }

    void givenEmptyName_thenException() {
        assertThrows(AssertionError.class,
                () -> EventZone.builder(UUID.randomUUID().toString()).name("").build());
    }

    @Test
    void givenNullCapacity_thenException() {
        assertThrows(AssertionError.class,
                () -> EventZone.builder(UUID.randomUUID().toString()).capacity(null).build());
    }

    @Test
    void givenNullPrice_thenException() {
        assertThrows(AssertionError.class,
                () -> EventZone.builder(UUID.randomUUID().toString()).price(null).build());
    }

    @Test
    void givenNullNumbered_thenException() {
        assertThrows(AssertionError.class,
                () -> EventZone.builder(UUID.randomUUID().toString()).numbered(null).build());
    }
}
