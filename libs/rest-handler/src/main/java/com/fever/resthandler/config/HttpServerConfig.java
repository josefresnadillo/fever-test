package com.fever.resthandler.config;

import java.util.Optional;

/**
 * HttpServerConfig interface contains the methods that must be implemented in order to retrieve
 * the configuration of your http server. This could be: host, port...
 * Feel free to add the configuration needed for your HTTP server.
 */
public interface HttpServerConfig {
    Optional<String> getHost();
    Optional<Integer> getPort();
}
