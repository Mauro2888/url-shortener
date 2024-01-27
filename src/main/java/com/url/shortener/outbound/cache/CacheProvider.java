package com.url.shortener.outbound.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.url.shortener.domain.create.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ApplicationScoped
public class CacheProvider {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final Cache<String, Url> cache;
    @Inject
    public CacheProvider() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
    }
    public Optional<Url> getFromCache(String userId) {
        log.info("Get from cache %s".formatted(userId));
        return Optional.ofNullable(cache.getIfPresent(userId));
    }

    public void cacheResult(String userId, Url url) {
        cache.put(userId, url);
    }

    public void logAll() {
        log.info("Cache size %s".formatted(cache.estimatedSize()));
        cache.asMap().forEach((key, value) -> log.info("Key %s, value %s".formatted(key, value)));
    }

}
