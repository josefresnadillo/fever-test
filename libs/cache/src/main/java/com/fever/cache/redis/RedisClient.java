package com.fever.cache.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fever.cache.compression.CompressionUtils;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.redis.client.Redis;
import io.vertx.reactivex.redis.client.RedisAPI;
import io.vertx.reactivex.redis.client.Response;
import io.vertx.redis.client.RedisOptions;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RedisClient {
  private static final String BODY_PARSE_ERROR = "Could not parse body: %s";

  private final ObjectMapper objectMapper = JsonMapper.builder()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .addModule(new JavaTimeModule())
      .build();

  private final ObjectMapper objectMapperIgnoreAnnotations = JsonMapper.builder()
      .configure(MapperFeature.USE_ANNOTATIONS, false)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .addModule(new JavaTimeModule())
      .build();

  private final RedisAPI redisAPI;

  public RedisClient(final RedisOptions redisOptions, final Vertx vertx) {
    final Redis redis = Redis.createClient(vertx, redisOptions);
    redisAPI = RedisAPI.api(redis);
  }

  public <T> Maybe<T> find(final String key,
                           final Class<T> returnClass) {
    return redisAPI.rxGet(key).map(Response::toBuffer)
        .map(jsonData -> {
          final String content = CompressionUtils.decompressAndDecodeFromBase64(jsonData);
          return readBody(content, returnClass);
        });
  }

  public <T> Maybe<T> findIgnoreAnnotations(final String key,
                                            final Class<T> returnClass) {
    return redisAPI.rxGet(key).map(Response::toBuffer)
        .map(jsonData -> {
          final String content = CompressionUtils.decompressAndDecodeFromBase64(jsonData);
          return readBodyIgnoreAnnotations(content, returnClass);
        });
  }

  public <T> Maybe<List<T>> findList(final String key, final Class<T> returnClass) {
    return redisAPI.rxGet(key).map(Response::toBuffer)
        .map(jsonData -> {
          final String content = CompressionUtils.decompressAndDecodeFromBase64(jsonData);
          return new JsonArray(content).stream().map(it -> ((JsonObject) it).mapTo(returnClass)).collect(Collectors.toList());
        });
  }

  public Completable save(final String key, final Object value, final long expirationInSeconds) throws IOException {
    final String json = JsonObject.mapFrom(value).encode();
    return Completable.fromMaybe(redisAPI.rxSetex(key,
        String.valueOf(expirationInSeconds),
        CompressionUtils.compressAndEncodeToBase64(json)));
  }

  public Completable saveList(final String key, final Object value, final long expirationInSeconds) throws IOException {
    final String json = (new JsonArray((List<?>) value)).toString();
    return Completable.fromMaybe(redisAPI.rxSetex(key,
        String.valueOf(expirationInSeconds),
        CompressionUtils.compressAndEncodeToBase64(json)));
  }

  public <T> T readBody(final String body,
                        final Class<T> classz) {
    try {
      return objectMapper.readValue(body, classz);
    } catch (Exception e) {
      throw new RuntimeException(String.format(BODY_PARSE_ERROR, body));
    }
  }

  public <T> T readBodyIgnoreAnnotations(final String body,
                                         final Class<T> classz) {
    try {
      return objectMapperIgnoreAnnotations.readValue(body, classz);
    } catch (Exception e) {
      throw new RuntimeException(String.format(BODY_PARSE_ERROR, body));
    }
  }
}
