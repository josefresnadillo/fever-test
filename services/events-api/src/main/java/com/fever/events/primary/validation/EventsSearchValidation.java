package com.fever.events.primary.validation;

import com.fever.events.primary.model.QueryParam;
import com.fever.exceptions.domain.RequestValidation;
import com.fever.exceptions.exception.BadRequestException;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.ext.web.RoutingContext;

public class EventsSearchValidation {
  private static final String QUERY_PARAM_FROM = QueryParam.STARTS_AT.getValue();
  private static final String QUERY_PARAM_TO = QueryParam.ENDS_TO.getValue();
  private EventsSearchValidation() {
    // do nothing
  }

  public static void validate(final RoutingContext routingContext) {
    final MultiMap params = routingContext.queryParams();

    final RequestValidation.Builder requestValidationBuilder = RequestValidation.builder();
    LocalDateTimeValidation.validateOptional(params.get(QUERY_PARAM_FROM), QUERY_PARAM_FROM, requestValidationBuilder);
    LocalDateTimeValidation.validateOptional(params.get(QUERY_PARAM_TO), QUERY_PARAM_TO, requestValidationBuilder);

    final RequestValidation requestValidation = requestValidationBuilder.build();
    if (requestValidation.hasErrors()) {
      throw new BadRequestException(requestValidation.getErrorMessages());
    }
  }
}
