package com.url.shortener.inbound.find;

import com.url.shortener.domain.find.UrlShorterFindService;
import common.be.common.rest.qualifier.InboundDelegate;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

@RequestScoped
@InboundDelegate
public class UrlShorterFindDelegate implements UrlShorterFindResource {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlShorterFindService urlShorterFindService;

    @Inject
    public UrlShorterFindDelegate(UrlShorterFindService urlShorterFindService) {
        this.urlShorterFindService = urlShorterFindService;
    }

    @Override
    public CompletionStage<Response> find(String code) {
        log.log(INFO,()-> "Searching url code %s".formatted(code));
        var promise = urlShorterFindService.find(code)
                .thenApply(response -> Response.status(Response.Status.FOUND)
                        .location(URI.create(response.originalUrl()))
                        .build());
        promise.exceptionally(exception -> {
            log.log(SEVERE, "Error during fetching url", exception);
            return null;
        });
        return promise;
    }
}
