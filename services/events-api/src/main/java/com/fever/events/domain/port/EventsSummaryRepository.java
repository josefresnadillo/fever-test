package com.fever.events.domain.port;

import com.fever.events.domain.model.EventsAggregate;
import io.reactivex.Single;

import java.time.LocalDateTime;

public interface EventsSummaryRepository {
  Single<EventsAggregate> listByCriteria(final LocalDateTime from, final LocalDateTime to);

  Single<EventsAggregate> save(final EventsAggregate eventsAggregate);
}
