package com.fever.exceptions.domain;

import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.domain.model.FeverErrorType;
import java.util.ArrayList;
import java.util.List;

public final class RequestValidation {

  private final List<ErrorMessage> errorMessages;

  private RequestValidation(final List<ErrorMessage> inputErrors) {
    this.errorMessages = List.copyOf(inputErrors);
  }

  public List<ErrorMessage> getErrorMessages() {
    return List.copyOf(errorMessages);
  }

  public boolean hasErrors() {
    return !errorMessages.isEmpty();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final List<ErrorMessage> errors;

    public Builder() {
      this.errors = new ArrayList<>();
    }

    public Builder badRequestError(final FeverErrorType errorType, final String inputError) {
      errors.add(new ErrorMessage(errorType, inputError));
      return this;
    }

    public RequestValidation build() {
      return new RequestValidation(errors);
    }
  }
}
