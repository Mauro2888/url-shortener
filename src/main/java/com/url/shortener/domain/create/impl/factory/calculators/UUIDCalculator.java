package com.url.shortener.domain.create.impl.factory.calculators;

import com.url.shortener.domain.create.impl.factory.Calculate;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class UUIDCalculator implements Calculate {
    @Override
    public Url generate(String url) {
        var result = UUID.randomUUID().toString().replaceAll("-", "")
                .substring(0, 8).toLowerCase();
        return Url.builder()
                .withCode(result)
                .withOriginalUrl(url)
                .withShortUrl("http://shortner.com/".concat(result))
                .build();
    }

    @Override
    public Algorithm algoType() {
        return Algorithm.UUID;
    }
}
