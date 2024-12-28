package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class UrlHashingServiceCalculator {

    private final AlgorithmSelector algorithmSelector;

    @Inject
    public UrlHashingServiceCalculator(AlgorithmSelector algorithmSelector) {
        this.algorithmSelector = algorithmSelector;
    }

    public CompletionStage<Url> hashingUrl(String url, Algorithm algorithm) {
        var service = algorithmSelector.get(algorithm);
        return completedStage(service.generate(url));
    }
}
