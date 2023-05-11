package com.fever.events.module;

import com.fever.events.primary.handler.*;
import com.fever.mongodb.client.EventsMongoClient;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(
        modules = {
                CommonsModule.class,
                ServiceModule.class,
                RepositoryModule.class
        }
)
public interface HandlerComponents {
    HealthCheckRestHandlerImpl buildHealthCheckRestHandler();

    SwaggerUrlRestHandlerImpl buildDefaultSwaggerRestHandler();

    EventsRestHandlerImpl buildEventsRestHandlerImpl();

    EventsMongoClient buildEventsMongoClient();
}
