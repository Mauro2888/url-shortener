package com.url.shortener.vm;

import common.be.common.builder.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(name = "url")
public record UrlShortenerViewModel(
    String originalUrl, AlgorithmViewModel algorithmViewModel) {

    public static UrlShortenerViewModelBuilder builder() {
        return new UrlShortenerViewModelBuilder();
    }
}
