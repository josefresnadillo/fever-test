package com.fever.events.verticles;

import com.fever.events.module.DaggerHandlerComponents;
import com.fever.mongodb.client.EventsMongoClient;
import com.fever.mongodb.client.model.MongoReadMessage;
import io.vertx.core.json.Json;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;

public class MongoVerticle extends AbstractVerticle {

  private static final String TOPIC = "MongoRead";

  private static EventBus eventBus;

  private EventsMongoClient eventsMongoClient;

  public MongoVerticle() {
    // do nothing
  }

  @Override
  public void start() {
    eventBus = vertx.eventBus();
    final var handlerComponents = DaggerHandlerComponents.create();
    this.eventsMongoClient = handlerComponents.buildEventsMongoClient();
    eventBus.localConsumer(TOPIC).handler(this::handlerAsyncEvents);
  }

  public static EventBus getEventBus() {
    return eventBus;
  }

  private void handlerAsyncEvents(final Message<Object> msg) {
    final MongoReadMessage messageBody = Json.decodeValue(msg.body().toString(), MongoReadMessage.class);
    this.eventsMongoClient.listEvents(messageBody.getFrom(), messageBody.getTo(), msg);
  }
}
