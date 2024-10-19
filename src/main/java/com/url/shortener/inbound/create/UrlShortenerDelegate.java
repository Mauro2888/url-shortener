package com.url.shortener.inbound.create;


import com.url.shortener.domain.create.UrlShortenerCreateService;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlShortener;
import com.url.shortener.domain.create.model.UrlShortenerBuilder;
import com.url.shortener.vm.UrlShortenerViewModel;
import common.qualifier.ResourceDelegate;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@ResourceDelegate
public class UrlShortenerDelegate implements UrlShortenerCreateResource {
    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlShortenerCreateService urlShortenerCreateService;

    @Inject
    public UrlShortenerDelegate(UrlShortenerCreateService urlShortenerCreateService) {
        this.urlShortenerCreateService = urlShortenerCreateService;
    }

    @Override
    public CompletionStage<Url> create(UrlShortenerViewModel urlShortenerViewModel) {
        log.log(Level.INFO,()-> "Generating short url from url: %s".formatted(urlShortenerViewModel.originalUrl()));
        var request = UrlShortenerBuilder.builder()
                .withAlgorithm(Algorithm.valueOf(urlShortenerViewModel.algorithmViewModel().name()))
                .withUrl(urlShortenerViewModel.originalUrl())
                .build();
        var promise = urlShortenerCreateService.create(request);
        promise.thenApply(unused -> Response.status(201).build());
        promise.exceptionally(exception -> {
            log.log(Level.SEVERE, "Error creating url", exception);
            return null;
        });
        return promise;

    }
}
