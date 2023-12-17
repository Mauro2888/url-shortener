package com.url.shortener.vm;

import jakarta.json.bind.annotation.JsonbTransient;

public record UrlShortenerViewModel(String originalUrl,
                                    AlgorithmViewModel algorithmViewModel) {

    public static class Builder {
        private String originalUrl;
        private AlgorithmViewModel algorithmViewModel;

        public Builder withOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public Builder withAlgorithmType(AlgorithmViewModel algorithmViewModel) {
            this.algorithmViewModel = algorithmViewModel;
            return this;
        }

        public UrlShortenerViewModel build() {
            return new UrlShortenerViewModel(originalUrl, algorithmViewModel);
        }
    }
    @JsonbTransient
    public static Builder builder() {
        return new Builder();
    }
}
