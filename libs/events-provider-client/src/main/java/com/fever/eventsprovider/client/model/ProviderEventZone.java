package com.fever.eventsprovider.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fever.utils.StringUtils;

import java.util.Objects;

public class ProviderEventZone {

  @JsonProperty("zone_id")
  private final String id;

  @JsonProperty("name")
  private final String name;

  @JsonProperty("capacity")
  private final Integer capacity;

  @JsonProperty("price")
  private final Double price;

  @JsonProperty("numbered")
  private final Boolean numbered;

  public ProviderEventZone() {
    this.id = StringUtils.EMPTY;
    this.name = StringUtils.EMPTY;;
    this.capacity = 0;
    this.price = 0.0d;
    this.numbered = Boolean.FALSE;
  }
  public ProviderEventZone(final String id,
                           final String name,
                           final Integer capacity,
                           final Double price,
                           final Boolean numbered) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
    this.price = price;
    this.numbered = numbered;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public Double getPrice() {
    return price;
  }

  public Boolean getNumbered() {
    return numbered;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProviderEventZone that = (ProviderEventZone) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(capacity, that.capacity) && Objects.equals(price, that.price) && Objects.equals(numbered, that.numbered);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, capacity, price, numbered);
  }

  @Override
  public String toString() {
    return "ProviderEventZone{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", capacity=" + capacity +
            ", price=" + price +
            ", numbered=" + numbered +
            '}';
  }
}

