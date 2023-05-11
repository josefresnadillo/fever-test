package com.fever.restclient.response.known;

import com.fever.exceptions.domain.model.FeverErrorType;
import com.fever.exceptions.exception.BadRequestException;
import com.fever.exceptions.exception.ResourceNotFoundException;
import com.fever.exceptions.exception.UnauthorizedException;
import com.fever.exceptions.exception.UnexpectedException;
import com.fever.restclient.response.BaseResponseTreatmentImpl;
import com.fever.restclient.response.Rule;
import io.reactivex.Single;

public class DefaultBaseResponseTreatment extends BaseResponseTreatmentImpl {

  public DefaultBaseResponseTreatment() {
    super();
    this.withRule(200, responseSuccess());
    this.withRule(201, responseSuccess());
    this.withRule(202, responseSuccess());
    this.withRule(204, responseSuccessEmptyBody());
    this.withRule(400, responseBadRequestError());
    this.withRule(401, responseUnauthorizedError());
    this.withRule(404, responseNotFoundError());
    this.withRule(DEFAULT_BEHAVIOR, responseDefault());
  }

  public static Rule responseSuccess() {
    return (response, options) -> Single.just(response);
  }

  public static Rule responseSuccessEmptyBody() {
    return (response, options) -> {
      logResponseInfo(response, options);
      return Single.just(response);
    };
  }

  public static Rule responseUnauthorizedError() {
    return (response, options) -> {
      logResponseError(response, options);
      return Single.error(new UnauthorizedException(FeverErrorType.UNKNOWN, "UnAuthorized"));
    };
  }

  public static Rule responseBadRequestError() {
    return (response, options) -> {
      logResponseError(response, options);
      return Single.error(new BadRequestException(
          FeverErrorType.UNKNOWN,
          String.format("[%s] %s", options.getRequestName(), response.bodyAsString())));
    };
  }

  public static Rule responseNotFoundError() {
    return (response, options) -> {
      logResponseError(response, options);
      return Single.error(new ResourceNotFoundException(FeverErrorType.RESOURCE_NOT_FOUND, "Resource not found"));

    };
  }

  public static Rule responseDefault() {
    return (response, options) -> {
      logResponseError(response, options);
      return Single.error(new UnexpectedException(
          FeverErrorType.UNKNOWN,
          String.format("[%s] %s", options.getRequestName(), response.bodyAsString())));
    };
  }
}