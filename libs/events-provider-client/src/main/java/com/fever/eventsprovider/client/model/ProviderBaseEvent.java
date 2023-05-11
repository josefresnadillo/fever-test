package com.fever.eventsprovider.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fever.utils.StringUtils;
import java.util.Objects;

public class ProviderBaseEvent {
  @JsonProperty("base_event_id")
  private final String id;
  @JsonProperty("sell_mode")
  private final SellModeType sellModeType;
  @JsonProperty("title")
  private final String title;
  @JsonProperty("event")
  private final ProviderEvent event;

  public ProviderBaseEvent() {
    this.id = StringUtils.EMPTY;
    this.sellModeType = SellModeType.ONLINE;
    this.title = StringUtils.EMPTY;;
    this.event = new ProviderEvent();
  }
  public ProviderBaseEvent(final String id,
                           final SellModeType sellModeType,
                           final String title,
                           final ProviderEvent event) {
    this.id = id;
    this.sellModeType = sellModeType;
    this.title = title;
    this.event = event;
  }

  public String getId() {
    return id;
  }

  public SellModeType getSellModeType() {
    return sellModeType;
  }

  public String getTitle() {
    return title;
  }

  public ProviderEvent getEvent() {
    return event;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProviderBaseEvent that = (ProviderBaseEvent) o;
    return Objects.equals(id, that.id) && sellModeType == that.sellModeType && Objects.equals(title, that.title) && Objects.equals(event, that.event);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sellModeType, title, event);
  }

  @Override
  public String toString() {
    return "ProviderBaseEvent{" +
            "id='" + id + '\'' +
            ", sellModeType=" + sellModeType +
            ", title='" + title + '\'' +
            ", event=" + event +
            '}';
  }
}

