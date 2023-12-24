package com.url.shortener.inbound.find;

import com.url.shortener.common.qualifier.ResourceDelegate;
import com.url.shortener.domain.find.UrlShorterFindService;
import com.url.shortener.inbound.find.mapper.UrlShorterMapper;
import com.url.shortener.vm.UrlShortenerViewModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

@RequestScoped
@ResourceDelegate
public class UrlShorterFindDelegate implements UrlShorterFindResource {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final UrlShorterFindService urlShorterFindService;
    private final UrlShorterMapper urlShorterMapper;

    @Inject
    public UrlShorterFindDelegate(UrlShorterFindService urlShorterFindService,
                                  UrlShorterMapper urlShorterMapper) {
        this.urlShorterFindService = urlShorterFindService;
        this.urlShorterMapper = urlShorterMapper;
    }

    @Override
    public CompletionStage<UrlShortenerViewModel> find(String code) {
        log.log(INFO,()-> "Searching url code %s".formatted(code));
        var promise = urlShorterFindService.find(code)
                .thenApply(urlShorterMapper);
        promise.exceptionally(exception -> {
            log.log(SEVERE, "Error during fetching url", exception);
            return null;
        });
        return promise;
    }
}
