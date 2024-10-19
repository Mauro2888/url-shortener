package com.url.shortener.domain.create.model;

import common.builder.Builder;

@Builder
public record UrlShortener(String url,Algorithm algorithm) {
}
