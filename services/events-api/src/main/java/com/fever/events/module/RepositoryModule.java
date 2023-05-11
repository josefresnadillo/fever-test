package com.fever.events.module;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fever.cache.FeverCache;
import com.fever.events.config.Config;
import com.fever.events.domain.port.EventsSummaryRepository;
import com.fever.events.secondary.dao.eventsprovider.EventsProviderDao;
import com.fever.events.secondary.repository.EventsSummaryRepositoryImpl;
import com.fever.eventsprovider.client.EventsProviderClient;
import com.fever.eventsprovider.client.EventsProviderClientImpl;
import com.fever.events.secondary.dao.database.MongoDbDao;
import com.fever.eventsprovider.client.config.EventsProviderConfig;
import com.fever.mongodb.client.EventsMongoClient;
import com.fever.mongodb.client.EventsMongoClientImpl;
import com.fever.restclient.RestClient;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

import javax.inject.Singleton;

@Module
public class RepositoryModule {


    // REPOSITORIES

    @Provides
    @Singleton
    public EventsSummaryRepository provideEventsSummaryRepository(final EventsProviderDao eventsProviderDao,
                                                                  final MongoDbDao mongoDbDao) {
        return new EventsSummaryRepositoryImpl(eventsProviderDao, mongoDbDao);
    }


    // DAOs

    @Provides
    @Singleton
    public EventsProviderDao provideEventsProviderDao(final EventsProviderClient client) {
        return new EventsProviderDao(client);
    }

    @Provides
    @Singleton
    public MongoDbDao provideMongoDbDao(final Vertx vertx, final EventsMongoClient eventsMongoClient) {
        return new MongoDbDao(eventsMongoClient, vertx.eventBus());
    }


    // CLIENTS

    @Provides
    @Singleton
    public EventsProviderClient provideMEventsProviderClient(final RestClient restClient,
                                                             final FeverCache feverCache,
                                                             final Config config) {
        DatabindCodec.mapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DatabindCodec.mapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new EventsProviderClientImpl(
                restClient,
                feverCache,
                new EventsProviderConfig(config.getEventsProviderHost(),
                        Integer.parseInt(config.getCacheTtl())));
    }

    @Provides
    @Singleton
    public EventsMongoClient provideMEventsMongoClient(final Vertx vertx, final MongoClient mongoClient) {
        return new EventsMongoClientImpl(vertx, mongoClient);
    }
}
