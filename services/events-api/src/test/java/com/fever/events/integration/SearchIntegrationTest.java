package com.fever.events.integration;

import com.fever.events.domain.port.EventsSummaryRepository;
import com.fever.events.mother.SecondaryMother;
import com.fever.events.primary.api.EventList;
import com.fever.events.primary.model.EventsSearch;
import com.fever.events.primary.model.QueryParam;
import com.fever.events.primary.service.EventsServiceImpl;
import com.fever.events.secondary.dao.database.MongoDbDao;
import com.fever.events.secondary.dao.eventsprovider.EventsProviderDao;
import com.fever.events.secondary.repository.EventsSummaryRepositoryImpl;
import com.fever.eventsprovider.client.EventsProviderClient;
import com.fever.mongodb.client.EventsMongoClient;
import io.reactivex.Single;
import io.vertx.reactivex.core.MultiMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchIntegrationTest {

  @BeforeAll
  public static void setUp() {
    // do nothing
  }

  @Test
  void givenSearch_whenAllEventsInDateRange_thenResultIsNotEmpty() {

    final EventsMongoClient eventsMongoClient = mock(EventsMongoClient.class);
    when(eventsMongoClient.listEvents((LocalDateTime) any(), any(), any())).thenReturn(Single.just(List.of(SecondaryMother.simpleMongoEvent())));
    when(eventsMongoClient.saveEvents(any())).thenReturn(Single.just(List.of(SecondaryMother.simpleMongoEvent())));
    final MongoDbDao mongoDbDao = new MongoDbDao(eventsMongoClient, null);

    final EventsProviderClient eventsProviderClient = mock(EventsProviderClient.class);
    when(eventsProviderClient.listEvents()).thenReturn(Single.just(SecondaryMother.simpleProviderEventList()));
    final EventsProviderDao eventsProviderDao = new EventsProviderDao(eventsProviderClient);

    final EventsSummaryRepository repository = new EventsSummaryRepositoryImpl(eventsProviderDao, mongoDbDao);

    final MultiMap search = MultiMap.caseInsensitiveMultiMap();
    search.add(QueryParam.STARTS_AT.getValue(), "1970-07-30T21:00:00");
    search.add(QueryParam.ENDS_TO.getValue(), "2050-07-31T21:30:00");

    final EventsServiceImpl primaryEventService = new EventsServiceImpl(repository);
    final EventList result = primaryEventService.listByCriteria(new EventsSearch(search)).blockingGet();

    assertFalse(result.getEvents().isEmpty());
  }

  @Test
  void givenSearch_whenAllEventsNotInDateRange_thenResultIsEmpty() {
    final EventsMongoClient eventsMongoClient = mock(EventsMongoClient.class);
    when(eventsMongoClient.listEvents((LocalDateTime) any(), any(), any())).thenReturn(Single.just(List.of(SecondaryMother.simpleMongoEvent())));
    when(eventsMongoClient.saveEvents(any())).thenReturn(Single.just(List.of(SecondaryMother.simpleMongoEvent())));
    final MongoDbDao mongoDbDao = new MongoDbDao(eventsMongoClient, null);

    final EventsProviderClient eventsProviderClient = mock(EventsProviderClient.class);
    when(eventsProviderClient.listEvents()).thenReturn(Single.just(SecondaryMother.simpleProviderEventList()));
    final EventsProviderDao eventsProviderDao = new EventsProviderDao(eventsProviderClient);

    final EventsSummaryRepository repository = new EventsSummaryRepositoryImpl(eventsProviderDao, mongoDbDao);

    final MultiMap search = MultiMap.caseInsensitiveMultiMap();
    search.add(QueryParam.STARTS_AT.getValue(), "2030-07-30T21:00:00");
    search.add(QueryParam.ENDS_TO.getValue(), "2050-07-31T21:30:00");

    final EventsServiceImpl primaryEventService = new EventsServiceImpl(repository);
    final EventList result = primaryEventService.listByCriteria(new EventsSearch(search)).blockingGet();

    assertTrue(result.getEvents().isEmpty());
  }
}
