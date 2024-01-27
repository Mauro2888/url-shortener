package com.url.shortener.outbound.cache;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.repository.UrlShorterCreateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class CacheRepositoryImpl  {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final CacheProvider provider;
    private final UrlShorterCreateRepository urlShorterCreateRepository;

    @Inject
    public CacheRepositoryImpl(CacheProvider provider, UrlShorterCreateRepository urlShorterCreateRepository) {
        this.provider = provider;
        this.urlShorterCreateRepository = urlShorterCreateRepository;
    }

    public CompletionStage<Url> get(Url url) {
     var fromCache = provider.getFromCache(url.code());
     if (fromCache.isPresent()) {
         log.info("Get from cache %s".formatted(url.code()));
         return completedStage(fromCache.get());
     } else {
         log.info("Cache is empty persist to db %s".formatted(url.code()));
         return urlShorterCreateRepository.create(url)
                 .thenApply(shortUrl -> {
                     provider.cacheResult(url.code(), shortUrl);
                     return shortUrl;
                 });
     }
    }
}
