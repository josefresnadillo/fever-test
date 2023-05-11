package com.fever.mongodb.client.adapter;

import com.fever.mongodb.client.model.MongoEvent;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class MongoEventAdapter {

    private MongoEventAdapter() {
        // do nothing
    }

    public static List<MongoEvent> adapt(final String json) {
        return new JsonArray(json)
                .stream()
                .map(it -> ((JsonObject) it).mapTo(MongoEvent.class))
                .collect(Collectors.toList());
    }
}
