package com.url.shortener.domain.model;

public record Url(String originalUrl,
                  String shortUrl) {

    public static class Builder {
        private String originalUrl;
        private String shortUrl;

        public Builder originalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public Builder shortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
            return this;
        }

        public Url build() {
            return new Url(originalUrl, shortUrl);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
