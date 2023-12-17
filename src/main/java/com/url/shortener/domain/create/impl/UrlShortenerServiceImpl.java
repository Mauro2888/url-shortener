package com.url.shortener.domain.create.impl;

import com.url.shortener.domain.create.UrlShortenerCreateService;
import com.url.shortener.domain.create.impl.factory.UrlHashingCalculator;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlShortener;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UrlShortenerServiceImpl implements UrlShortenerCreateService {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlHashingCalculator urlHashingCalculator;

    @Inject
    public UrlShortenerServiceImpl(UrlHashingCalculator urlHashingCalculator) {
        this.urlHashingCalculator = urlHashingCalculator;
    }

    @Override
    public CompletionStage<Url> create(UrlShortener urlShortener) {
        log.log(Level.INFO,()-> "Generate short url with algo %s".formatted(urlShortener.algorithm().name()));
        var promise = urlHashingCalculator.hashingUrl(urlShortener.url(), urlShortener.algorithm());
        promise.exceptionally(exception -> {
            log.log(Level.SEVERE, "Error creating url", exception);
            return null;
        });
        return promise;
    }
}
