package com.fever.events.config;

import com.fever.resthandler.config.SharedConfig;

import java.util.List;

public class Config extends SharedConfig {

  // Cache
  private static final String GET_CACHE_HOST = "cache.host";
  private static final String GET_CACHE_PORT = "cache.port";
  private static final String GET_CACHE_DB = "cache.db";
  private static final String GET_CACHE_ENABLED = "cache.enabled";

  private static final String GET_CACHE_TTL = "cache.ttlInSeconds";

  // throttling
  private static final String GET_THROTTLING_ENABLED = "throttling.enabled";
  private static final String GET_THROTTLING_CAPACITY = "throttling.capacity";
  private static final String GET_THROTTLING_INTERVAL_IN_SECONDS = "throttling.intervalInSeconds";

  // MongoDB
  private static final String GET_MONGO_DB_HOST = "mongodb.host";
  private static final String GET_MONGO_DB_PORT = "mongodb.port";

  // Events Provider
  private static final String EVENTS_PROVIDER_HOST = "eventsProvider.host";

  // Bundles
  private static final String GET_EVENTS_URL = "/search";


  private static Config instance;

  private Config(final List<String> endpoints) {
    super(endpoints);
    var conf = Config.getSuperInstance();
    this.init(conf.getConfig().orElse(null));
  }

  public static synchronized Config getInstance() {
    if (instance == null) {
      instance = new Config(List.of());
    }
    return instance;
  }

  // Cache
  public String getCacheHost() {
    return this.getDeployedValue(GET_CACHE_HOST);
  }

  public String getCachePort() {
    return this.getDeployedValue(GET_CACHE_PORT);
  }

  public String getCacheDb() {
    return this.getDeployedValue(GET_CACHE_DB);
  }

  public String getCacheEnabled() {
    return this.getDeployedValue(GET_CACHE_ENABLED);
  }

  public String getCacheTtl() {
    return this.getDeployedValue(GET_CACHE_TTL);
  }

  // Throttling
  public String getThrottlingEnabled() {
    return this.getDeployedValue(GET_THROTTLING_ENABLED);
  }
  public String getThrottlingCapacity() {
    return this.getDeployedValue(GET_THROTTLING_CAPACITY);
  }
  public String getThrottlingIntervalInSeconds() {
    return this.getDeployedValue(GET_THROTTLING_INTERVAL_IN_SECONDS);
  }


  // Mongo
  public String getMongoHost() {
    return this.getDeployedValue(GET_MONGO_DB_HOST);
  }

  public String getMongoPort() {
    return this.getDeployedValue(GET_MONGO_DB_PORT);
  }


  // Events Provider
  public String getEventsProviderHost() {
    return this.getDeployedValue(EVENTS_PROVIDER_HOST);
  }

  public String getEventsUrl() {
    return GET_EVENTS_URL;
  }

}
