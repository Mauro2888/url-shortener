package com.url.shortener.domain.impl;

import com.url.shortener.domain.UrlShortenerCreateService;
import com.url.shortener.domain.model.Url;
import com.url.shortener.domain.model.UrlShortener;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class UrlShortenerServiceImpl implements UrlShortenerCreateService {

    private final UrlHashingCalculator urlHashingCalculator;

    @Inject
    public UrlShortenerServiceImpl(UrlHashingCalculator urlHashingCalculator) {
        this.urlHashingCalculator = urlHashingCalculator;
    }

    @Override
    public CompletionStage<Url> create(UrlShortener urlShortener) {
        var hash = urlHashingCalculator.calculateUrl(urlShortener.url());
        return completedStage(hash);
    }
}
