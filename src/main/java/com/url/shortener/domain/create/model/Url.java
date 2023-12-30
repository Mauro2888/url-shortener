package com.url.shortener.domain.create.model;

import common.builder.Builder;

@Builder
public record Url(String originalUrl,
                  String shortUrl,
                  String code) {
}
