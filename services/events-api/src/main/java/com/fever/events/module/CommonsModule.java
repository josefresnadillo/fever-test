package com.fever.events.module;

import com.fever.cache.FeverCache;
import com.fever.events.config.Config;
import com.fever.events.primary.service.RateLimiter;
import com.fever.restclient.RestClient;
import com.fever.restclient.RestClientImpl;
import dagger.Module;
import dagger.Provides;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;
import io.vertx.reactivex.ext.web.client.WebClient;
import org.redisson.jcache.configuration.RedissonConfiguration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.inject.Singleton;

@Module
public class CommonsModule {

    private static final String MONGO_DB_URL = "mongodb://%s:%s";
    private static final String MONGO_DB_COLLECTION = "feverevents";
    private static final String THROTTLING_CACHE_BUCKET4J = "cache";
    private static final String THROTTLING_REDIS_URL = "redis://%s:%s";

    @Provides
    @Singleton
    public Config provideConfig() {
        return Config.getInstance();
    }

    @Provides
    @Singleton
    public Vertx provideVertx() {
        return Vertx.currentContext().owner();
    }

    @Provides
    @Singleton
    public WebClient provideWebClient(final Vertx vertx) {
        return WebClient.create(vertx);
    }

    @Provides
    @Singleton
    public FeverCache provideCache(final Vertx vertx,
                                   final Config config) {
        return FeverCache.builder(vertx)
                .host(config.getCacheHost())
                .port(Integer.parseInt(config.getCachePort()))
                .redisDb(Integer.parseInt(config.getCacheDb()))
                .enabled(Boolean.parseBoolean(config.getCacheEnabled()))
                .build();
    }

    @Provides
    @Singleton
    public MongoClient provideMongoClient(final Vertx vertx,
                                          final Config config) {
        return MongoClient.createShared(vertx, new JsonObject()
                .put("url", String.format(MONGO_DB_URL, config.getMongoHost(), config.getMongoPort()))
                .put("db_name", MONGO_DB_COLLECTION));
    }

    @Provides
    @Singleton
    public RestClient provideBaseExternalService(final WebClient webClient) {
        return new RestClientImpl(webClient);
    }

    @Provides
    @Singleton
    public org.redisson.config.Config config() {
        final org.redisson.config.Config config = new org.redisson.config.Config();
        config.useSingleServer().setAddress(
                String.format(THROTTLING_REDIS_URL,
                        Config.getInstance().getCacheHost(),
                        Config.getInstance().getCachePort()));
        return config;
    }

    @Provides
    @Singleton
    public CacheManager cacheManager(final org.redisson.config.Config config,
                                     final Config vertxConfig) {
        final CacheManager manager = Caching.getCachingProvider().getCacheManager();
        if (Boolean.parseBoolean(vertxConfig.getThrottlingEnabled())) {
            manager.createCache(THROTTLING_CACHE_BUCKET4J, RedissonConfiguration.fromConfig(config));
        }
        return manager;
    }

    @Provides
    @Singleton
    public RateLimiter rateLimiter(final CacheManager cacheManager,
                                   final Config config) {
        return Boolean.parseBoolean(config.getThrottlingEnabled()) ?
                new RateLimiter(
                        new JCacheProxyManager<>(cacheManager.getCache(THROTTLING_CACHE_BUCKET4J)),
                        Integer.valueOf(config.getThrottlingCapacity()),
                        Integer.valueOf(config.getThrottlingIntervalInSeconds()),
                        Boolean.parseBoolean(Config.getInstance().getThrottlingEnabled())):
                new RateLimiter(null,
                        Integer.valueOf(config.getThrottlingCapacity()),
                        Integer.valueOf(config.getThrottlingIntervalInSeconds()),
                        Boolean.parseBoolean(Config.getInstance().getThrottlingEnabled()));
    }

}
