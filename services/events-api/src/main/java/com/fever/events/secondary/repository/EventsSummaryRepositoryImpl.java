package com.fever.events.secondary.repository;

import com.fever.events.domain.model.EventsAggregate;
import com.fever.events.domain.port.EventsSummaryRepository;
import com.fever.events.secondary.adapter.domain.EventAdapter;
import com.fever.events.secondary.adapter.mongodb.MongoEventAdapter;
import com.fever.events.secondary.dao.eventsprovider.EventsProviderDao;
import com.fever.mongodb.client.model.MongoEvent;
import io.reactivex.Single;
import com.fever.events.secondary.dao.database.MongoDbDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventsSummaryRepositoryImpl implements EventsSummaryRepository {

    private final EventsProviderDao eventsProviderDao;
    private final MongoDbDao mongoDbDao;

    public EventsSummaryRepositoryImpl(final EventsProviderDao eventsProviderDao,
                                       final MongoDbDao mongoDbDao) {
        this.eventsProviderDao = eventsProviderDao;
        this.mongoDbDao = mongoDbDao;
    }

    @Override
    public Single<EventsAggregate> listByCriteria(final LocalDateTime from,
                                                  final LocalDateTime to) {
        return Single.zip(
                eventsProviderDao.listByCriteria(),
                mongoDbDao.listByCriteria(from, to),
                (providerEvents, mongoEvents) -> EventsAggregate.builder(UUID.randomUUID())
                        .from(from)
                        .to(to)
                        .oldEvents(EventAdapter.adaptOld(mongoEvents)) // Mongodb events are old events
                        .newEvents(EventAdapter.adaptNew(providerEvents)) // Provider events are maybe new events
                        .build());
    }

    @Override
    public Single<EventsAggregate> save(final EventsAggregate eventsAggregate) {
        final List<MongoEvent> mongoEvents = MongoEventAdapter.adapt(eventsAggregate.getNewEvents());
        return mongoDbDao.save(mongoEvents)
                .map(vents -> eventsAggregate);
    }
}
