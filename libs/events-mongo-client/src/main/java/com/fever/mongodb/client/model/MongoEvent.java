package com.fever.mongodb.client.model;

import com.fever.utils.StringUtils;

import java.util.List;
import java.util.Objects;

public class MongoEvent {
    private final String id;
    private final String title;
    private final String from;
    private final String to;
    private final List<MongoEventZone> zones;
    private final Boolean newEvent;

    public MongoEvent() {
        this.id = StringUtils.EMPTY;
        this.title =StringUtils.EMPTY;
        this.from = StringUtils.EMPTY;
        this.to = StringUtils.EMPTY;
        this.zones = List.of();
        this.newEvent = Boolean.FALSE;
    }

    public MongoEvent(final Builder builder) {
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<MongoEventZone> getZones() {
        return zones;
    }

    public Boolean getNewEvent() {
        return newEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoEvent that = (MongoEvent) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(zones, that.zones) && Objects.equals(newEvent, that.newEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, from, to, zones, newEvent);
    }

    @Override
    public String toString() {
        return "MongoEvent{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", zones=" + zones +
                ", newEvent=" + newEvent +
                '}';
    }

    public static Builder builder(final String id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final String id;
        private String title = StringUtils.EMPTY;
        private String from = StringUtils.EMPTY;
        private String to = StringUtils.EMPTY;
        private List<MongoEventZone> zones = List.of();
        private Boolean newEvent = false;

        private Builder(final String id) {
            this.id = id;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder from(final String from) {
            this.from = from;
            return this;
        }

        public Builder to(final String to) {
            this.to = to;
            return this;
        }

        public Builder zones(final List<MongoEventZone> zones) {
            this.zones = zones;
            return this;
        }

        public Builder newEvent(final Boolean newEvent) {
            this.newEvent = newEvent;
            return this;
        }

        public MongoEvent build() {
            return new MongoEvent(this);
        }
    }
}