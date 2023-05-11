package com.fever.mongodb.client;

import com.fever.mongodb.client.model.MongoEvent;
import io.reactivex.Single;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsMongoClient {
    Single<List<MongoEvent>> listEvents(final LocalDateTime from,
                                        final LocalDateTime to,
                                        final EventBus eventBus);

    Single<List<MongoEvent>> saveEvents(final List<MongoEvent> mongoEvents);

    void listEvents(final String from,
                    final String to,
                    final Message<Object> msg);

}
