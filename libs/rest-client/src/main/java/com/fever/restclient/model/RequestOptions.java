package com.fever.restclient.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestOptions {

  private final String url;
  private final String requestName;
  private final Object payload;
  private final Object formPayload;
  private final int retries;
  private final Map<String, Object> pathParams;
  private final Map<String, Object> queryParams;
  private final Map<String, String> headers;
  private final Map<String, Object> extraLogFields;

  private RequestOptions(final Builder builder) {
    this.url = builder.url;
    this.requestName = builder.requestName;
    this.payload = builder.payload;
    this.formPayload = builder.formPayload;
    this.retries = builder.retries;
    this.headers = builder.headers;
    this.pathParams = builder.pathParams;
    this.queryParams = builder.queryParams;
    this.extraLogFields = builder.extraLogFields;
  }

  public String getUrl() {
    return url;
  }

  public String getRequestName() {
    return requestName;
  }

  public Object getPayload() {
    return payload;
  }

  public Object getFormPayload() {
    return formPayload;
  }

  public int getRetries() {
    return retries;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Map<String, Object> getPathParams() {
    return pathParams;
  }

  public Map<String, Object> getQueryParams() {
    return queryParams;
  }

  public Map<String, Object> getExtraLogFields() {
    return extraLogFields;
  }

  public String getComposedQueryParams() {
    return getQueryParams().entrySet()
        .stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining("&"));
  }

  public String getComposedUrl() {
    return getUrl() + "?" + getComposedQueryParams();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RequestOptions that = (RequestOptions) o;
    return retries == that.retries && Objects.equals(url, that.url) && Objects.equals(requestName,
        that.requestName) && Objects.equals(payload, that.payload) && Objects.equals(formPayload, that.formPayload) && Objects.equals(pathParams,
        that.pathParams) && Objects.equals(queryParams, that.queryParams) && Objects.equals(headers, that.headers) && Objects.equals(extraLogFields
        , that.extraLogFields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, requestName, payload, formPayload, retries, pathParams, queryParams, headers, extraLogFields);
  }

  @Override
  public String toString() {
    return "RequestOptions{" +
        ", url='" + url + '\'' +
        ", requestName='" + requestName + '\'' +
        ", payload=" + payload +
        ", formPayload=" + formPayload +
        ", retries=" + retries +
        ", pathParams=" + pathParams +
        ", queryParams=" + queryParams +
        ", headers=" + headers +
        ", extraLogFields=" + extraLogFields +
        '}';
  }

  public static Builder builder(final String url,
                                final String requestName) {
    return new Builder(url, requestName);
  }

  public static final class Builder {
    private final String url;
    private final String requestName;
    private Object payload = null;
    private Object formPayload = null;
    private int retries = 0;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, Object> pathParams = new HashMap<>();
    private final Map<String, Object> queryParams = new HashMap<>();
    private final Map<String, Object> extraLogFields = new HashMap<>();

    private Builder(final String url,
                    final String requestName) {
      this.url = url;
      this.requestName = requestName;
    }

    public Builder withPayload(final Object payload) {
      this.payload = payload;
      return this;
    }

    public Builder withFormPayload(final Object formPayload) {
      this.formPayload = formPayload;
      return this;
    }

    public Builder withRetries(final int retries) {
      this.retries = retries;
      return this;
    }

    public Builder withHeaders(final Map<String, String> headers) {
      this.headers.putAll(headers);
      return this;
    }

    public Builder withPathParams(final Map<String, String> pathParams) {
      this.pathParams.putAll(pathParams);
      return this;
    }

    public Builder withQueryParams(final Map<String, String> queryParams) {
      this.queryParams.putAll(queryParams);
      return this;
    }

    public Builder withExtraLogFields(final Map<String, Object> extraLogFields) {
      this.extraLogFields.putAll(extraLogFields);
      return this;
    }

    public RequestOptions build() {
      return new RequestOptions(this);
    }
  }
}
