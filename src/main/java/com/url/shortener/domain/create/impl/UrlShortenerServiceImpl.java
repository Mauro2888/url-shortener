package com.url.shortener.domain.create.impl;

import com.url.shortener.domain.create.UrlShortenerCreateService;
import com.url.shortener.domain.create.impl.factory.UrlHashingCalculator;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlShortener;
import com.url.shortener.domain.create.repository.UrlShorterCreateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UrlShortenerServiceImpl implements UrlShortenerCreateService {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlHashingCalculator urlHashingCalculator;
    private final UrlShorterCreateRepository urlShorterCreateRepository;

    @Inject
    public UrlShortenerServiceImpl(UrlHashingCalculator urlHashingCalculator,
                                   UrlShorterCreateRepository urlShorterCreateRepository) {
        this.urlHashingCalculator = urlHashingCalculator;
        this.urlShorterCreateRepository = urlShorterCreateRepository;
    }

    @Override
    public CompletionStage<Url> create(UrlShortener urlShortener) {
        log.log(Level.INFO,()-> "Generate short url with algo %s".formatted(urlShortener.algorithm().name()));
        return urlHashingCalculator.hashingUrl(urlShortener.url(), urlShortener.algorithm())
                .thenCompose(urlShorterCreateRepository::create);
    }
}
