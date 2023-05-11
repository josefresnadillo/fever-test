package com.fever.events.primary.service;

import com.fever.events.domain.port.EventsSummaryRepository;
import com.fever.events.primary.adapter.EventListAdapter;
import com.fever.events.primary.api.EventList;
import com.fever.events.primary.model.EventsSearch;
import io.reactivex.Single;

import javax.inject.Inject;

public class EventsServiceImpl implements EventsService {

    final EventsSummaryRepository eventsSummaryRepository;

    @Inject
    public EventsServiceImpl(final EventsSummaryRepository eventsSummaryRepository) {
        this.eventsSummaryRepository = eventsSummaryRepository;
    }

    @Override
    public Single<EventList> listByCriteria(final EventsSearch search) {
        return eventsSummaryRepository.listByCriteria(search.getFrom(), search.getTo())
                .flatMap(eventsSummaryRepository::save) // save new events to repository
                .map(eventsAggregate -> eventsAggregate.getEventsInRange(search.getFrom(), search.getTo())) // Filtering is done after save, in order to save as much events as possible
                .map(EventListAdapter::adapt);
    }
}

