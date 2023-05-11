package com.fever.events.domain.model;

import com.fever.utils.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Event {
    private final String id;
    private final String title;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final List<EventZone> zones;
    private final Boolean newEvent;

    public Event(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.from = builder.from;
        this.to = builder.to;
        this.zones = builder.zones;
        this.newEvent = builder.newEvent;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public List<EventZone> getZones() {
        return zones;
    }

    public Boolean getIsNewEvent() {
        return newEvent;
    }

    public BigDecimal getMinPrice() {
        final Comparator<EventZone> minFirst = Comparator.comparing(EventZone::getPrice);
        return getPriceWithComparator(minFirst);
    }

    public BigDecimal getMaxPrice() {
        final Comparator<EventZone> maxFirst = Comparator.comparing(EventZone::getPrice).reversed();
        return getPriceWithComparator(maxFirst);
    }

    private BigDecimal getPriceWithComparator(final Comparator<EventZone> comparator) {
        return getZones()
                .stream()
                .sorted(comparator)
                .map(EventZone::getPrice)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(title, event.title) &&
                Objects.equals(from, event.from) &&
                Objects.equals(to, event.to) &&
                Objects.equals(zones, event.zones); // Dont't compare isNewEvent
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, from, to, zones);
    } // Without isNewEvent

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", zones=" + zones +
                ", isNewEvent=" + newEvent +
                '}';
    }

    public static Builder builder(final String id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final String id;
        private String title = StringUtils.EMPTY;
        private LocalDateTime from = LocalDateTime.MIN;
        private LocalDateTime to = LocalDateTime.MAX;
        private final List<EventZone> zones;
        private Boolean newEvent = false;

        private Builder(final String id) {
            assert id != null && !id.isEmpty();
            this.id = id;
            this.zones = new ArrayList<>();
        }

        public Builder title(final String title) {
            assert title != null && !title.isEmpty();
            this.title = title;
            return this;
        }

        public Builder from(final LocalDateTime from) {
            assert from != null;
            this.from = from;
            return this;
        }

        public Builder to(final LocalDateTime to) {
            assert to != null;
            this.to = to;
            return this;
        }

        public Builder zones(final List<EventZone> zones) {
            assert zones != null;
            this.zones.addAll(zones);
            return this;
        }

        public Builder newEvent(final Boolean newEvent) {
            this.newEvent = newEvent == null ? this.newEvent : newEvent;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}