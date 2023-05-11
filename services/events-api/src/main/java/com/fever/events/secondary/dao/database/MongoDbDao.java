package com.fever.events.secondary.dao.database;

import com.fever.mongodb.client.EventsMongoClient;
import com.fever.mongodb.client.model.MongoEvent;
import io.reactivex.Single;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.reactivex.core.eventbus.EventBus;

import java.time.LocalDateTime;
import java.util.List;

public class MongoDbDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbDao.class.getName());

    private static final String NO_SAVE_MESSAGE = "Nothing to save";

    private final EventsMongoClient eventsMongoClient;
    private final EventBus eventBus;

    public MongoDbDao(final EventsMongoClient eventsMongoClient,
                      final EventBus eventBus) {
        this.eventsMongoClient = eventsMongoClient;
        this.eventBus = eventBus;
    }

    public Single<List<MongoEvent>> listByCriteria(final LocalDateTime from,
                                                   final LocalDateTime to) {
        return eventsMongoClient.listEvents(from, to, eventBus);
    }

    public Single<List<MongoEvent>> save(final List<MongoEvent> events) {
        if (events.isEmpty()) {
            LOGGER.info(NO_SAVE_MESSAGE);
            return Single.just(events);
        }
        return eventsMongoClient.saveEvents(events);
    }
}
