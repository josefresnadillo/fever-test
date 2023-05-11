package com.fever.mongodb.client;

import com.fever.mongodb.client.adapter.JsonObjectAdapter;
import com.fever.mongodb.client.adapter.MongoEventAdapter;
import com.fever.mongodb.client.model.MongoEvent;
import com.fever.mongodb.client.model.MongoReadMessage;
import io.reactivex.Single;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.reactivex.core.Vertx;

import java.time.LocalDateTime;
import java.util.List;

public class EventsMongoClientImpl implements EventsMongoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsMongoClientImpl.class.getName());

    private static final String SAVED_EVENT_LOG = "Saved event: %s";

    private static final String MONGO_TOPIC = "MongoRead";

    private static final String MONGO_COLLECTION = "events";

    private final MongoClient mongoClient;

    private final Vertx vertx;

    public EventsMongoClientImpl(final Vertx vertx,
                                 final MongoClient mongoClient) {
        this.vertx = vertx;
        this.mongoClient = mongoClient;
    }

    @Override
    public Single<List<MongoEvent>> listEvents(final LocalDateTime from,
                                               final LocalDateTime to,
                                               final EventBus eventBus) {
        final MongoReadMessage mongoReadMessage = new MongoReadMessage(from.toString(), to.toString());
        return eventBus.rxRequest(
                        MONGO_TOPIC,
                        Json.encode(mongoReadMessage),
                        new DeliveryOptions())
                .flatMap(res -> {
                    final List<MongoEvent> list = MongoEventAdapter.adapt(res.body().toString());
                    return Single.just(list);
                });
    }

    @Override
    public void listEvents(final String from,
                           final String to,
                           final Message<Object> msg) {
        final JsonObject query = JsonObjectAdapter.adaptToQuery(from, to);
        mongoClient.find(MONGO_COLLECTION, query, res -> {
            if (res.succeeded()) {
                LOGGER.info("Reading from MongoDB was successful: " + res.result());
                msg.reply(res.result().toString());
            }
        });
    }

    @Override
    public Single<List<MongoEvent>> saveEvents(final List<MongoEvent> mongoEvents) {
        final List<JsonObject> jsons = JsonObjectAdapter.adapt(mongoEvents);
        vertx.executeBlocking(future -> {
            jsons.forEach(json -> mongoClient.save(MONGO_COLLECTION, json, res -> {
                if (res.succeeded()) {
                    LOGGER.info("Save to MongoDB was successful");
                }
            }));
            future.complete(mongoEvents);
        }, res -> LOGGER.info(String.format(SAVED_EVENT_LOG, res.result())));
        return Single.just(mongoEvents);
    }
}
