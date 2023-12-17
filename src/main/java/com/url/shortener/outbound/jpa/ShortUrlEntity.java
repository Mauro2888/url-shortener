package com.url.shortener.outbound.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
@NamedQuery(name = ShortUrlEntity.URL_FIND_BY_CODE, query = "SELECT u FROM ShortUrlEntity u WHERE u.code = :CODE")
public class ShortUrlEntity extends BaseEntity {

    public static final String URL_FIND_BY_CODE = "URL_FIND_BY_CODE";
    @Column(name = "original_url",unique = true)
    private String originalUrl;
    @Column(name = "short_url",unique = true)
    private String shortUrl;
    @Column(name = "hashcode",unique = true)
    private String code;

    public ShortUrlEntity() {
    }

    public ShortUrlEntity(String originalUrl, String shortUrl, String code) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.code = code;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getCode() {
        return code;
    }

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

        public ShortUrlEntity build() {
            return new ShortUrlEntity(originalUrl, shortUrl,code);
        }
    }

    public static Builder builder() {
        return new Builder();
    }


}
