package com.url.shortener.domain.create.impl;

import com.url.shortener.domain.create.UrlShortenerCreateService;
import com.url.shortener.domain.create.impl.factory.UrlHashingServiceCalculator;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlShortener;
import com.url.shortener.domain.create.repository.UrlShorterCreateRepository;
import com.url.shortener.domain.find.repository.UrlShorterFindByLongUrlRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UrlShortenerServiceImpl implements UrlShortenerCreateService {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlHashingServiceCalculator urlHashingServiceCalculator;
    private final UrlShorterCreateRepository urlShorterCreateRepository;

    @Inject
    public UrlShortenerServiceImpl(
        UrlHashingServiceCalculator urlHashingServiceCalculator,
        UrlShorterCreateRepository urlShorterCreateRepository) {
        this.urlHashingServiceCalculator = urlHashingServiceCalculator;
        this.urlShorterCreateRepository = urlShorterCreateRepository;
    }

    @Override
    public CompletionStage<Url> create(UrlShortener urlShortener) {
        log.info(()-> "Generate short url with algo %s".formatted(urlShortener.algorithm().name()));
        return urlHashingServiceCalculator.hashingUrl(urlShortener.url(), urlShortener.algorithm())
                .thenCompose(urlShorterCreateRepository::create);
    }
}
