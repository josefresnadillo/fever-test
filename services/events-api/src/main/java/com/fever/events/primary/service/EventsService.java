package com.fever.events.primary.service;

import com.fever.events.primary.api.EventList;
import com.fever.events.primary.model.EventsSearch;
import io.reactivex.Single;

public interface EventsService {

  Single<EventList> listByCriteria(final EventsSearch search);
}
