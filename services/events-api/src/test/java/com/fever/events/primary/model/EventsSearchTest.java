package com.fever.events.primary.model;

import io.vertx.reactivex.core.MultiMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventsSearchTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenEmptyQueryParams_thenFromAndToAreDefaults() {
        final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
        final EventsSearch eventsSearch = new EventsSearch(queries);
        assertEquals(EventsSearch.getDefaultStartAt(), eventsSearch.getFrom());
        assertEquals(EventsSearch.getDefaultEndsAt(), eventsSearch.getTo());
    }

    @Test
    void givenValidQueryParams_thenValidationIsOk() {
        final LocalDateTime startAt = LocalDateTime.of(2021, 7, 30, 21, 0, 0, 0);
        final LocalDateTime endsAt = LocalDateTime.of(2021, 7, 31, 21, 30, 0, 0);

        final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
        queries.add(QueryParam.STARTS_AT.getValue(), "2021-07-30T21:00:00");
        queries.add(QueryParam.ENDS_TO.getValue(), "2021-07-31T21:30:00");

        final EventsSearch eventsSearch = new EventsSearch(queries);

        assertEquals(startAt, eventsSearch.getFrom());
        assertEquals(endsAt, eventsSearch.getTo());
    }

    @Test
    void givenOnlyStartsAt_thenEndsAtIsDefault() {
        final LocalDateTime startAt = LocalDateTime.of(2021, 7, 30, 21, 0, 0, 0);

        final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
        queries.add(QueryParam.STARTS_AT.getValue(), "2021-07-30T21:00:00");

        final EventsSearch eventsSearch = new EventsSearch(queries);

        assertEquals(startAt, eventsSearch.getFrom());
        assertEquals(EventsSearch.getDefaultEndsAt(), eventsSearch.getTo());
    }

    @Test
    void givenOnlyEndsAt_thenStartAtIsDefault() {
        final LocalDateTime endsAt = LocalDateTime.of(2021, 7, 31, 21, 30, 0, 0);

        final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
        queries.add(QueryParam.ENDS_TO.getValue(), "2021-07-31T21:30:00");

        final EventsSearch eventsSearch = new EventsSearch(queries);

        assertEquals(EventsSearch.getDefaultStartAt(), eventsSearch.getFrom());
        assertEquals(endsAt, eventsSearch.getTo());
    }
}
