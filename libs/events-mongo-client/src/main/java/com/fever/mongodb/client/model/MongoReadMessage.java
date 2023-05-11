package com.fever.mongodb.client.model;

import com.fever.utils.StringUtils;

import java.util.Objects;

public class MongoReadMessage {

    private final String from;
    private final String to;

    public MongoReadMessage() {
        this.from = StringUtils.EMPTY;
        this.to = StringUtils.EMPTY;
    }

    public MongoReadMessage(final String from, final String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoReadMessage that = (MongoReadMessage) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "MongoReadMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
