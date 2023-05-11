package com.fever.resthandler.config;

import com.fever.utils.EnvUtils;
import com.fever.utils.StringUtils;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Optional;

/**
 * SharedConfig implements the methods needed to retrieve the app configuration. Each of the interfaces that implements
 * represents a set of configuration properties used in different parts of the codebase. In this way, we can keep those
 * parts as simple and descriptive as possible because they would receive only the configuration they actually need.
 * Every bit of configuration is returned as an Optional to avoiding the use of nulls and preventing possible errors.
 */
public class SharedConfig implements HttpServerConfig {

  public static final String SERVER = "server";
  public static final String DEFAULT_HOST = "127.0.0.1";
  public static final int DEFAULT_PORT = 8080;
  private static SharedConfig instance;

  /**
   * The JsonObject which contains the app configuration.
   */
  private JsonObject config;

  /**
   * @deprecated
   */
  @Deprecated(since = "1.0", forRemoval = true)
  public SharedConfig() {
    // do nothing
  }

  public SharedConfig(final List<String> inboundPaths) {
    // do nothing
  }

  /**
   * @return a reference to the single instance of SharedConfig.
   */
  public static synchronized SharedConfig getSuperInstance() {
    if (instance == null) {
      instance = new SharedConfig(List.of());
    }
    return instance;
  }

  /**
   * Initializes the app configuration.
   *
   * @param configInit JsonObject that contains the initial configuration
   */
  public void init(final JsonObject configInit) {
    config = configInit;
  }

  /**
   * @return the configuration of the whole app.
   */
  public Optional<JsonObject> getConfig() {
    return Optional.of(config);
  }

  public Optional<String> getContext() {
    return Optional.ofNullable(config.getJsonObject(SERVER).getString("context"));
  }

  @Override
  public Optional<String> getHost() {
    return Optional.ofNullable(config.getJsonObject(SERVER).getString("host"));
  }

  @Override
  public Optional<Integer> getPort() {
    return Optional.ofNullable(config.getJsonObject(SERVER).getInteger("port"));
  }

  /**
   * Gets the string value from the JsonObject config with the given pattern The pattern consists of a chain of elements
   * separated by dots in which each element is a jsonObject Example: thirdParties.chuck.url gets the url from {
   * "thirdParties" : { "chuck" : { "url" : "https://api.chucknorris.io/jokes/random" } } }
   */
  public Object getValue(final String pattern) {
    var conf = config;
    final String[] accessors = pattern.split("\\.");
    final int numAccessors = accessors.length;
    final int lastAccessorIndex = numAccessors - 1;
    for (int i = 0; i < lastAccessorIndex; i++) {
      conf = conf.getJsonObject(accessors[i]);
    }

    return conf.getValue(accessors[lastAccessorIndex]);
  }

  /**
   * Gets the result of combining the string value from the JsonObject config with the given pattern and the string
   * value from the environment system values with the given pattern uppercase and dots replaced by underscore The
   * pattern consists of a chain of elements separated by dots in which each element is a jsonObject Example:
   * thirdParties.chuck.url gets the url from { "thirdParties" : { "chuck" : { "url" :
   * "https://api.chucknorris.io/jokes/random" } } } and checks THIRDPARTIES_CHUCK_URL system environment value
   */
  public String getDeployedValue(final String pattern) {
    final String environmentValue = EnvUtils.findEnv(pattern.toUpperCase().replace(".", "_"));
    String configFileValue = StringUtils.EMPTY;
    if (this.getValue(pattern) != null) {
      configFileValue = this.getValue(pattern).toString();
    }
    return (environmentValue != null && !environmentValue.isEmpty()) ? environmentValue:configFileValue;
  }

  public boolean isDebugEnabled() {
    return "true".equals(this.getDeployedValue("debugMode"));
  }
}
