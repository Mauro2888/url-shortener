package com.url.shortener.domain.create.impl.mapper;

import com.url.shortener.domain.create.model.HashCode;
import com.url.shortener.domain.create.model.Url;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.BiFunction;

@ApplicationScoped
public class UrlHashCodeMapper implements BiFunction<HashCode,String, Url> {
    @Override
    public Url apply(HashCode hashCode,String url) {
        return Url.builder()
                        .withCode(hashCode.value())
                        .withOriginalUrl(url)
                        .withShortUrl("http://shortner.com/".concat(hashCode.value()))
                        .build();
    }

}
