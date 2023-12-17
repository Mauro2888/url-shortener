package com.url.shortener.domain.create.model;

public record Url(String originalUrl,
                  String shortUrl,
                  String code) {

    public static class Builder {
        private String originalUrl;
        private String shortUrl;
        private String code;

        public Builder withOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public Builder withShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
            return this;
        }

        public Builder withCode(String code){
            this.code = code;
            return this;
        }

        public Url build() {
            return new Url(originalUrl, shortUrl,code);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
