package com.url.shortener.vm;

import common.be.common.builder.Builder;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
public record UrlShortenerViewModel(
    @Schema(example = "http://www.google.it")
    @NotNull String originalUrl,
    @JsonbProperty(value = "algorithm")
    @NotNull AlgorithmViewModel algorithmViewModel) {

    @JsonbTransient
    public static UrlShortenerViewModelBuilder builder() {
        return new UrlShortenerViewModelBuilder();
    }
}
