package com.url.shortener.vm;

import common.be.common.builder.Builder;
import jakarta.json.bind.annotation.JsonbTransient;

@Builder
public record UrlViewModel(String originalUrl,
                  String shortUrl,
                  String code) {

    @JsonbTransient
    public static UrlViewModelBuilder builder(){
        return new UrlViewModelBuilder();
    }
}
