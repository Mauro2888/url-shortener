package com.url.shortener.domain;

import com.url.shortener.domain.model.Url;
import com.url.shortener.domain.model.UrlShortener;

import java.util.concurrent.CompletionStage;

public interface UrlShortenerCreateService {
    CompletionStage<Url> create(UrlShortener urlShortener);
}
