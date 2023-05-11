package com.fever.eventsprovider.client.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class ProviderEvent   {
  @JsonProperty("event_start_date")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final LocalDateTime startDate;
  @JsonProperty("event_end_date")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final LocalDateTime endDate;
  @JsonProperty("sell_from")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final LocalDateTime sellFrom;
  @JsonProperty("sell_to")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final LocalDateTime sellTo;
  @JsonProperty("sold_out")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private final Boolean soldOut;
  @JsonProperty("zone")
  @JacksonXmlElementWrapper(useWrapping = false)
  private final List<ProviderEventZone> zones;

  public ProviderEvent() {
    this.startDate = LocalDateTime.MIN;
    this.endDate = LocalDateTime.MIN;
    this.sellFrom = LocalDateTime.MIN;
    this.sellTo = LocalDateTime.MIN;
    this.soldOut = Boolean.TRUE;
    this.zones = List.of();
  }

  public ProviderEvent(final LocalDateTime startDate,
                       final LocalDateTime endDate,
                       final LocalDateTime sellFrom,
                       final LocalDateTime sellTo,
                       final Boolean soldOut,
                       final List<ProviderEventZone> zones) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.sellFrom = sellFrom;
    this.sellTo = sellTo;
    this.soldOut = soldOut;
    this.zones = zones;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public LocalDateTime getSellFrom() {
    return sellFrom;
  }

  public LocalDateTime getSellTo() {
    return sellTo;
  }

  public Boolean getSoldOut() {
    return soldOut;
  }

  public List<ProviderEventZone> getZones() {
    return zones;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProviderEvent that = (ProviderEvent) o;
    return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(sellFrom, that.sellFrom) && Objects.equals(sellTo, that.sellTo) && Objects.equals(soldOut, that.soldOut) && Objects.equals(zones, that.zones);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, sellFrom, sellTo, soldOut, zones);
  }

  @Override
  public String toString() {
    return "ProviderEvent{" +
            "startDate=" + startDate +
            ", endDate=" + endDate +
            ", sellFrom=" + sellFrom +
            ", sellTo=" + sellTo +
            ", soldOut=" + soldOut +
            ", zones=" + zones +
            '}';
  }
}

