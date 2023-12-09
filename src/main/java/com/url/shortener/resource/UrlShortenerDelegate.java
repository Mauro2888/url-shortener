package com.url.shortener.resource;

import com.url.shortener.common.qualifier.Delegate;
import com.url.shortener.domain.UrlShortenerCreateService;
import com.url.shortener.domain.model.Url;
import com.url.shortener.domain.model.UrlShortener;
import com.url.shortener.vm.UrlShortenerViewModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Delegate
public class UrlShortenerDelegate implements UrlShortenerCreateResource {
    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlShortenerCreateService urlShortenerCreateService;

    @Inject
    public UrlShortenerDelegate(UrlShortenerCreateService urlShortenerCreateService) {
        this.urlShortenerCreateService = urlShortenerCreateService;
    }

    @Override
    public CompletionStage<Url> create(UrlShortenerViewModel urlShortenerViewModel) {
        var promise = urlShortenerCreateService.create(new UrlShortener(urlShortenerViewModel.originalUrl()));
        promise.thenApply(unused -> Response.status(201).build());
        promise.exceptionally(exception -> {
            log.log(Level.SEVERE, "Error creating url", exception);
            return null;
        });
        return promise;

    }
}
