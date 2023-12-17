package com.url.shortener.domain.impl.factory.calculators;

import com.url.shortener.domain.impl.factory.Calculate;
import com.url.shortener.domain.model.Algorithm;
import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class UUIDCalculator implements Calculate {
    @Override
    public CompletionStage<Url> generate(String url) {
        var result = UUID.randomUUID().toString().replaceAll("-", "")
                .substring(0, 8).toLowerCase();
        return completedStage(new Url(url, "http://shortner.com/".concat(result)));
    }

    @Override
    public Algorithm algoType() {
        return Algorithm.UUID;
    }
}
