package com.fever.events.primary.model;

import com.fever.utils.StringUtils;
import io.vertx.reactivex.core.MultiMap;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventsSearch {

    private static final String QUERY_PARAM_FROM = QueryParam.STARTS_AT.getValue();
    private static final String QUERY_PARAM_TO = QueryParam.ENDS_TO.getValue();

    private static final LocalDateTime DEFAULT_START_AT = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0);
    private static final LocalDateTime DEFAULT_ENDS_AT = LocalDateTime.of(2050, 1, 1, 0, 0, 0, 0);

    private final LocalDateTime from;
    private final LocalDateTime to;

    public EventsSearch(final MultiMap queryParams) {
        this.from = StringUtils.isNullOrEmptyOrWhiteSpace(queryParams.get(QUERY_PARAM_FROM)) ?
                DEFAULT_START_AT :
                LocalDateTime.parse(queryParams.get(QUERY_PARAM_FROM));
        this.to = StringUtils.isNullOrEmptyOrWhiteSpace(queryParams.get(QUERY_PARAM_TO))?
                DEFAULT_ENDS_AT :
                LocalDateTime.parse(queryParams.get(QUERY_PARAM_TO));
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public static LocalDateTime getDefaultStartAt() {
        return DEFAULT_START_AT;
    }

    public static LocalDateTime getDefaultEndsAt() {
        return DEFAULT_ENDS_AT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventsSearch that = (EventsSearch) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "EventsSearch{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
