package com.url.shortener.vm;

import common.builder.Builder;

@Builder
public record UrlShortenerViewModel(String originalUrl,
                                    AlgorithmViewModel algorithmViewModel) {
}
