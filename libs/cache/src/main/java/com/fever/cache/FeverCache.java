package com.fever.cache;

import com.fever.cache.redis.RedisClient;
import com.fever.exceptions.domain.model.FeverErrorType;
import com.fever.exceptions.exception.InternalServerErrorException;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.reactivex.core.Vertx;
import io.vertx.redis.client.RedisOptions;
import java.util.function.Supplier;

public class FeverCache {

  private static final String DEFAULT_HOST = "localhost";
  private static final int DEFAULT_PORT = 6379;
  private static final int DEFAULT_DB = 1;
  private static final boolean DEFAULT_ENABLED = false;

  private static final String REDIS_CONNECTION_STRING = "redis://%s:%s/%s";

  private final RedisClient redisClient;
  private final boolean enabled;

  private FeverCache(final Builder builder) {
    final RedisOptions redisOptions = new RedisOptions()
        .addConnectionString(String.format(REDIS_CONNECTION_STRING, builder.host, builder.port, builder.redisDb))
        .setMaxPoolSize(100);
    this.redisClient = new RedisClient(redisOptions, builder.vertx);
    this.enabled = builder.enabled;
  }

  public <T> Maybe<T> get(final String key,
                          final Class<T> classType) {
    if (this.enabled) {
      return redisClient.find(key, classType);
    }
    throw new InternalServerErrorException(FeverErrorType.INTERNAL_SERVER_ERROR, "Redis is not available");
  }

  public <T> Single<T> executeWithCache(final String key,
                                        final Supplier<Single<T>> supplier,
                                        final Class<T> classType,
                                        final int expirationTimeInSeconds) {
    return this.enabled ?
        findOrExecuteAndStore(key, supplier, classType, expirationTimeInSeconds):
        supplier.get();
  }

  private <T> Single<T> findOrExecuteAndStore(final String key,
                                              final Supplier<Single<T>> supplier,
                                              final Class<T> classType,
                                              final int expirationTimeInSeconds) {
    return redisClient.find(key, classType)
        .toSingle()
        .onErrorResumeNext(error -> executeAndStoreInCache(key, supplier, expirationTimeInSeconds));
  }


  private <T> Single<T> executeAndStoreInCache(final String key,
                                               final Supplier<Single<T>> supplier,
                                               int expirationTimeInSeconds) {
    final Single<T> cachedSingle = supplier.get().cache();
    cachedSingle.subscribe(result -> redisClient.save(key, result, expirationTimeInSeconds).subscribe(), Single::error);
    return cachedSingle;
  }

  public static Builder builder(final Vertx vertx) {
    return new Builder(vertx);
  }

  public static final class Builder {
    private final Vertx vertx;
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int redisDb = DEFAULT_DB;
    private boolean enabled = DEFAULT_ENABLED;

    private Builder(final Vertx vertx) {
      this.vertx = vertx;
    }

    public Builder host(final String value) {
      this.host = value != null ? value:this.host;
      return this;
    }

    public Builder port(final int value) {
      this.port = value;
      return this;
    }

    public Builder redisDb(final int value) {
      this.redisDb = value;
      return this;
    }

    public Builder enabled(final boolean value) {
      this.enabled = value;
      return this;
    }

    public FeverCache build() {
      return new FeverCache(this);
    }
  }
}