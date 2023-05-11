package com.fever.events.mother;

import com.fever.eventsprovider.client.model.ProviderBaseEvent;
import com.fever.eventsprovider.client.model.ProviderEvent;
import com.fever.eventsprovider.client.model.ProviderEventList;
import com.fever.eventsprovider.client.model.SellModeType;
import com.fever.mongodb.client.model.MongoEvent;
import com.fever.mongodb.client.model.MongoEventZone;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SecondaryMother {

    public static final LocalDateTime startAt = LocalDateTime.of(2023, 1, 1, 10, 0, 0,0);
    public static final LocalDateTime endsAt = LocalDateTime.of(2023, 1, 1, 11, 0, 0,0);

    public static final String NEW_EVENT_ID_1 = "Event1";
    public static final String OLD_EVENT_ID_1 = "oldEvent1";

    public static final BigDecimal ZONE_1_PRICE = BigDecimal.valueOf(10);
    public static final BigDecimal ZONE_2_PRICE = BigDecimal.valueOf(20);

    public static MongoEvent simpleMongoEvent() {
        return MongoEvent.builder(NEW_EVENT_ID_1)
                .from(startAt.toString())
                .to(endsAt.toString())
                .title("New Event 1")
                .newEvent(true)
                .zones(List.of(simpleEventZone1()))
                .build();
    }

    public static MongoEventZone simpleEventZone1(){
        return MongoEventZone.builder("zone1")
                .numbered(true)
                .capacity(10)
                .price(ZONE_1_PRICE)
                .name("Zone expensive")
                .build();
    }

    public static ProviderEventList simpleProviderEventList() {
        return new ProviderEventList("1", List.of(simpleProviderBaseEvent()));
    }

    public static ProviderBaseEvent simpleProviderBaseEvent() {
        return new ProviderBaseEvent("1",
                SellModeType.ONLINE,
               "title",
                simpleProviderEvent());
    }

    public static ProviderEvent simpleProviderEvent() {
        final LocalDateTime startAt = LocalDateTime.of(2021, 7, 30, 21, 0, 0, 0);
        final LocalDateTime endsAt = LocalDateTime.of(2021, 8, 30, 21, 0, 0, 0);
        return new ProviderEvent(startAt,
                endsAt,
                startAt,
                endsAt,
                false,
                List.of());
    }
}
