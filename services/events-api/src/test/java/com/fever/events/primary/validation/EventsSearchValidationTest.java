package com.fever.events.primary.validation;

import com.fever.events.primary.model.QueryParam;
import com.fever.exceptions.exception.BadRequestException;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventsSearchValidationTest {

  @BeforeAll
  public static void setUp() {
    // do nothing
  }

  @Test
  void givenRoutingContext_whenNotQueryParams_then_validationIsOk() {
    final RoutingContext routingContext = mock(RoutingContext.class);

    final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
    when(routingContext.queryParams()).thenReturn(queries);

    assertDoesNotThrow(() -> EventsSearchValidation.validate(routingContext));
  }

  @Test
  void givenRoutingContext_whenQueryParamsAreValidLocalDateTimes_then_validationIsOk() {
    final RoutingContext routingContext = mock(RoutingContext.class);

    final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
    queries.add(QueryParam.STARTS_AT.getValue(),"2021-07-30T21:00:00");
    queries.add(QueryParam.ENDS_TO.getValue(),"2021-07-31T21:30:00");
    when(routingContext.queryParams()).thenReturn(queries);

    assertDoesNotThrow(() -> EventsSearchValidation.validate(routingContext));
  }

  @Test
  void givenRoutingContext_whenStartAtIsNotValidLocalDateTimes_then_validationIsNotOk() {
    final RoutingContext routingContext = mock(RoutingContext.class);

    final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
    queries.add(QueryParam.STARTS_AT.getValue(),"wrong");
    queries.add(QueryParam.ENDS_TO.getValue(),"2021-07-31T21:30:00");
    when(routingContext.queryParams()).thenReturn(queries);

    assertThrows(BadRequestException.class, () -> EventsSearchValidation.validate(routingContext));
  }

  @Test
  void givenRoutingContext_whenEndsAtIsNotValidLocalDateTimes_then_validationIsNotOk() {
    final RoutingContext routingContext = mock(RoutingContext.class);

    final MultiMap queries = MultiMap.caseInsensitiveMultiMap();
    queries.add(QueryParam.STARTS_AT.getValue(),"2021-07-30T21:00:00");
    queries.add(QueryParam.ENDS_TO.getValue(),"wrong");
    when(routingContext.queryParams()).thenReturn(queries);

    assertThrows(BadRequestException.class, () -> EventsSearchValidation.validate(routingContext));
  }
}
