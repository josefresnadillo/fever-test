package com.fever.events.domain.model;

import com.fever.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Objects;

public class EventZone {

  private final String id;
  private final String name;
  private final Integer capacity;
  private final BigDecimal price;
  private final Boolean numbered;

  public EventZone(final Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.capacity = builder.capacity;
    this.price = builder.price;
    this.numbered = builder.numbered;
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
  public BigDecimal getPrice() {
    return price;
  }
  public Boolean getNumbered() {
    return numbered;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventZone that = (EventZone) o;
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

  public static Builder builder(final String id) {
    return new Builder(id);
  }

  public static final class Builder {
    private final String id;
    private String name = StringUtils.EMPTY;
    private Integer capacity = 0;
    private BigDecimal price = BigDecimal.ZERO;
    private Boolean numbered = true;

    private Builder(final String id) {
      assert id != null && !id.isEmpty();
      this.id = id;
    }

    public Builder name(final String name) {
      assert name != null && !name.isEmpty();
      this.name = name;
      return this;
    }

    public Builder capacity(final Integer capacity) {
      assert capacity != null;
      this.capacity = capacity;
      return this;
    }

    public Builder price(final BigDecimal price) {
      assert price != null;
      this.price = price;
      return this;
    }

    public Builder numbered(final Boolean numbered) {
      assert numbered != null;
      this.numbered = numbered;
      return this;
    }

    public EventZone build() {
      return new EventZone(this);
    }
  }
}

