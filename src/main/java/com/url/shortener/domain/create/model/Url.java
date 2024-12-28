package com.url.shortener.domain.create.model;

import common.be.common.builder.Builder;

@Builder
public record Url(String originalUrl,
                  String shortUrl,
                  String code) {
    
    public static UrlBuilder builder(){
        return new UrlBuilder();
    }
}
