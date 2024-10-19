package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.vm.AlgorithmViewModel;
import common.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

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
        return completedStage(service.generate(url));
    }
}
