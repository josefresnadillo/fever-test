package com.fever.events.primary.service;

import com.fever.exceptions.domain.model.FeverErrorType;
import com.fever.exceptions.exception.ForbiddenException;
import com.fever.utils.StringUtils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.time.Duration;
import java.util.function.Supplier;

public class RateLimiter {
  private static final String KEY = "%s:%s";
  private static final String FORWARDED_FOR = "x-forwarded-for";

  private final ProxyManager<String> proxyManager;
  private final Integer capacity;
  private final Integer intervalInSeconds;
  private final Boolean isEnable;

  public RateLimiter(final ProxyManager<String> proxyManager,
                     final Integer capacity,
                     final Integer intervalInSeconds,
                     final Boolean isEnable) {
    this.proxyManager = proxyManager;
    this.capacity = capacity;
    this.intervalInSeconds = intervalInSeconds;
    this.isEnable = isEnable;
  }

  public Bucket resolveBucket(final String key) {
    final Bandwidth limit = Bandwidth.simple(this.capacity, Duration.ofSeconds(this.intervalInSeconds));
    final Supplier<BucketConfiguration> configSupplier = () -> (BucketConfiguration.builder()
        .addLimit(limit)
        .build());
    return proxyManager.builder().build(key, configSupplier);
  }

  public void checkThrottling(final RoutingContext routingContext,
                              final String throttlingUrlKey) {
    if (isEnable) {
      final HttpServerRequest req = routingContext.request();
      final String ip = adapt(StringUtils.valueOrEmpty(req.getHeader(FORWARDED_FOR)));
      final String key = String.format(KEY, ip, throttlingUrlKey);
      final Bucket bucket = resolveBucket(key);
      final boolean existTokens = bucket.tryConsume(1);
      if (!existTokens) {
        throw new ForbiddenException(FeverErrorType.FORBIDDEN_USER, "Too fast too furious!");
      }
    }
  }

  public static String adapt(final String forwardedFor) {
    return forwardedFor.split(",")[0];
  }

}
