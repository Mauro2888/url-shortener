package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.impl.mapper.UrlHashCodeMapper;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.HashCode;
import com.url.shortener.domain.create.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UrlHashingServiceCalculator {

    private final AlgorithmSelector algorithmSelector;
    private final UrlHashCodeMapper urlHashCodeMapper;

    @Inject
    public UrlHashingServiceCalculator(
        AlgorithmSelector algorithmSelector,
        UrlHashCodeMapper urlHashCodeMapper
    ) {
        this.algorithmSelector = algorithmSelector;
        this.urlHashCodeMapper = urlHashCodeMapper;
    }

    public CompletionStage<Url> hashingUrl(String url, Algorithm algorithm) {
        return algorithmSelector.get(algorithm)
            .generate(url)
            .thenApply(result -> urlHashCodeMapper.apply(result, url));
    }
}
