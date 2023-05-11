package com.fever.mongodb.client.adapter;

import com.fever.mongodb.client.model.MongoEvent;
import com.fever.utils.JsonUtils;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class JsonObjectAdapter {

    private static final String MONGO_ID = "_id";

    private static final String FROM_FIELD = "from";
    private static final String TO_FIELD = "to";

    private JsonObjectAdapter() {
        // do nothing
    }

    public static List<JsonObject> adapt(final List<MongoEvent> mongoEvents) {
        return mongoEvents
                .stream()
                .map(mongoEvent -> {
                    final JsonObject jsonObject = new JsonObject(JsonUtils.toJson(mongoEvent));
                    jsonObject.put(MONGO_ID, mongoEvent.getId());
                    return jsonObject;
                })
                .collect(Collectors.toList());
    }

    public static JsonObject adaptToQuery(final LocalDateTime from,
                                          final LocalDateTime to) {
        return new JsonObject()
                .put(FROM_FIELD, new JsonObject().put("$gte", from.toString()))
                .put(TO_FIELD, new JsonObject().put("$lte", to.toString()));
    }

    public static JsonObject adaptToQuery(final String from,
                                          final String to) {
        return new JsonObject()
                .put(FROM_FIELD, new JsonObject().put("$gte", from))
                .put(TO_FIELD, new JsonObject().put("$lte", to));
    }
}
