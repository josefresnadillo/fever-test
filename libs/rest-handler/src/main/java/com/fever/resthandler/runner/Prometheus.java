package com.fever.resthandler.runner;

import io.vertx.core.http.HttpServerOptions;

import io.vertx.micrometer.VertxPrometheusOptions;

public class Prometheus {

  private Prometheus() {
    // do nothing
  }

  public static VertxPrometheusOptions createVertxMetricsOptions() {
    return new VertxPrometheusOptions()
        .setPublishQuantiles(false)
        .setStartEmbeddedServer(true)
        .setEmbeddedServerOptions(new HttpServerOptions().setPort(9091))
        .setEnabled(true);
  }
}
