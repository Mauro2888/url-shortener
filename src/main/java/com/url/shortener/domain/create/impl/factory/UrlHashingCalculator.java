package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.common.exception.NotFoundException;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UrlHashingCalculator {

    private final AlgorithmConfiguration algorithmConfiguration;

    @Inject
    public UrlHashingCalculator(AlgorithmConfiguration algorithmConfiguration) {
        this.algorithmConfiguration = algorithmConfiguration;
    }

    public CompletionStage<Url> hashingUrl(String url, Algorithm algorithm) {
        var service = algorithmConfiguration.services().getOrDefault(algorithm, null);
        if (service == null) throw new NotFoundException("No services found for algo %s".formatted(algorithm));
        return service.generate(url);
    }
}
