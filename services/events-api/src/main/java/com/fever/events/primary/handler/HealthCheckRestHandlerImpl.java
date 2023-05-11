package com.fever.events.primary.handler;

import com.fever.resthandler.health.DefaultHealthCheckRestHandlerImpl;

import javax.inject.Inject;

public class HealthCheckRestHandlerImpl extends DefaultHealthCheckRestHandlerImpl {
  @Inject
  public HealthCheckRestHandlerImpl() {
    // do nothing
  }
}