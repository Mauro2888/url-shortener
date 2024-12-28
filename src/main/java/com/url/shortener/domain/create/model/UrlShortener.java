package com.url.shortener.domain.create.model;

import common.be.common.builder.Builder;

@Builder
public record UrlShortener(String url,Algorithm algorithm) {

    public static UrlShortenerBuilder builder() {
        return new UrlShortenerBuilder();
    }
}
