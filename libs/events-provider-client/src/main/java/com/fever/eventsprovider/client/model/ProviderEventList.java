package com.fever.eventsprovider.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fever.utils.StringUtils;

import java.util.List;
import java.util.Objects;

public class ProviderEventList {
  @JsonProperty("version")
  private final String version;
  @JsonProperty("output")
  private final List<ProviderBaseEvent> events;

  public ProviderEventList() {
    this.version = StringUtils.EMPTY;
    this.events = List.of();
  }
  public ProviderEventList(final String version,
                           final List<ProviderBaseEvent> events) {
    this.version = version;
    this.events = events;
  }

  public String getVersion() {
    return version;
  }

  public List<ProviderBaseEvent> getEvents() {
    return events;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProviderEventList that = (ProviderEventList) o;
    return Objects.equals(version, that.version) && Objects.equals(events, that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, events);
  }

  @Override
  public String toString() {
    return "ProviderEventList{" +
            "version='" + version + '\'' +
            ", events=" + events +
            '}';
  }
}

