package com.url.shortener.outbound.create.mapper;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

@ApplicationScoped
public class ShortUrlEntityMapper implements Function<Url, ShortUrlEntity> {
    @Override
    public ShortUrlEntity apply(Url url) {
        return ShortUrlEntity.builder()
                .withShortUrl(url.shortUrl())
                .withCode(url.code())
                .withOriginalUrl(url.originalUrl())
                .build();
    }
}
