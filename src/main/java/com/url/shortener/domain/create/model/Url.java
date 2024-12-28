package com.url.shortener.domain.create.model;

import common.be.common.builder.Builder;
import jakarta.json.bind.annotation.JsonbTransient;

@Builder
public record Url(String originalUrl,
                  String shortUrl,
                  String code) {

    @JsonbTransient
    public static UrlBuilder builder(){
        return new UrlBuilder();
    }
}
