package com.url.shortener.domain.create;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlShortener;

import java.util.concurrent.CompletionStage;

public interface UrlShortenerCreateService {
    CompletionStage<Url> create(UrlShortener urlShortener);
}
