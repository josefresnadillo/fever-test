package com.fever.eventsprovider.client;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fever.cache.FeverCache;
import com.fever.cache.adapter.CacheKeyAdapter;
import com.fever.eventsprovider.client.config.EventsProviderConfig;
import com.fever.eventsprovider.client.deserializer.CustomDeserializer;
import com.fever.restclient.RestClient;
import com.fever.restclient.model.RequestOptions;
import com.fever.restclient.response.KnownResponseTreatment;
import com.fever.utils.XmlUtils;
import io.reactivex.Single;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventsProviderClientBase {

    private static final String PREFIX_CACHE_KEY = "events-provider";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final RestClient restClient;
    private final FeverCache cache;
    private final EventsProviderConfig config;
    private final JavaTimeModule javaTimeModule;

    public EventsProviderClientBase(final RestClient restClient,
                                    final FeverCache cache,
                                    final EventsProviderConfig config) {
        this.restClient = restClient;
        this.cache = cache;
        this.config = config;
        javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new CustomDeserializer(formatter));
    }
    public EventsProviderConfig getConfig() {
        return config;
    }

    public <T> Single<T> getBodyFromRequest(final RequestOptions options,
                                            final Class<T> tClass) {
        final String cacheKey = CacheKeyAdapter.calculateCacheKey(
                options.getPathParams(),
                options.getQueryParams(),
                PREFIX_CACHE_KEY,
                options.getRequestName());
        return cache.executeWithCache(
                cacheKey,
                () -> restClient.getBodyFromRequest(options, KnownResponseTreatment.DEFAULT)
                        .map(response -> XmlUtils.readBodyIgnoreUnknowns(
                                response.bodyAsString(),
                                tClass,
                                this.javaTimeModule)),
                tClass,
                config.getTtl());
    }
}
