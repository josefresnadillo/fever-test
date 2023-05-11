package com.fever.resthandler.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fever.exceptions.domain.model.FeverErrorType;
import com.fever.exceptions.exception.InternalServerErrorException;
import com.fever.resthandler.model.FeverEndpoint;
import com.fever.resthandler.model.FeverEndpointBuilder;
import com.fever.utils.StringUtils;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DefaultSwaggerRestHandlerImpl implements SwaggerUrlRestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSwaggerRestHandlerImpl.class.getName());

  private static final String APPLICATION_HTML = "text/json";

  private static final String SWAGGER_IS_MSG = "Swagger file loaded";
  private static final String FILE_NOT_FOUND_ERROR_MSG = "Could not read swagger.url file from resources!";
  private static final String SWAGGER_ENDPOINT_URL = "/swagger";
  private static final String SWAGGER_YAML = "swagger.yaml";

  private final FeverEndpoint<JsonObject> swaggerUrlEndpoint;
  private final String swaggerUrl;

  public static DefaultSwaggerRestHandlerImpl create() {
    return new DefaultSwaggerRestHandlerImpl();
  }

  public DefaultSwaggerRestHandlerImpl() {
    this.swaggerUrl = convertYamlToJson(readSwaggerUrlFile());
    this.swaggerUrlEndpoint = new FeverEndpointBuilder<JsonObject>()
        .httpMethod(HttpMethod.GET)
        .contentTypeValue(APPLICATION_HTML)
        .endpointUrl(SWAGGER_ENDPOINT_URL)
        .buildWithSingle(this::getSwaggerUrl);
  }

  @Override
  public void addHandlersTo(Router router) {
    addHandlerTo(router,
            swaggerUrlEndpoint.getHttpMethod(),
            swaggerUrlEndpoint.getEndpointUrl(),
            routingContext -> swaggerUrlEndpoint.performAction(routingContext, false),
            null);
  }

  public Single<JsonObject> getSwaggerUrl(RoutingContext routingContext) {
    return Single.just(new JsonObject(this.swaggerUrl));
  }

  private String readSwaggerUrlFile() {
    try (InputStream systemResourceStream = ClassLoader.getSystemResourceAsStream(SWAGGER_YAML)) {
      if (Objects.isNull(systemResourceStream)) {
        LOGGER.error(FILE_NOT_FOUND_ERROR_MSG);
        throw new ExceptionInInitializerError(FILE_NOT_FOUND_ERROR_MSG);
      }
      final String swaggerUrlFile = IOUtils.toString(systemResourceStream, UTF_8);
      LOGGER.info(SWAGGER_IS_MSG);
      return swaggerUrlFile;
    } catch (Exception e) {
      LOGGER.error(FILE_NOT_FOUND_ERROR_MSG, e);
      throw new InternalServerErrorException(FeverErrorType.INTERNAL_SERVER_ERROR, FILE_NOT_FOUND_ERROR_MSG);
    }
  }

  private String convertYamlToJson(final String yaml) {
    try {
      final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
      final Object obj = yamlReader.readValue(yaml, Object.class);
      final ObjectMapper jsonWriter = new ObjectMapper();
      return jsonWriter.writeValueAsString(obj);
    } catch (Exception e) {
      return StringUtils.EMPTY;
    }
  }
}
