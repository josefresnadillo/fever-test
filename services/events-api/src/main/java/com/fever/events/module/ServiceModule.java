package com.fever.events.module;

import com.fever.events.domain.port.*;
import com.fever.events.primary.service.EventsService;
import com.fever.events.primary.service.EventsServiceImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ServiceModule {

  @Provides
  @Singleton
  public EventsService provideEventsService(final EventsSummaryRepository eventsSummaryRepository) {
    return new EventsServiceImpl(eventsSummaryRepository);
  }
}
