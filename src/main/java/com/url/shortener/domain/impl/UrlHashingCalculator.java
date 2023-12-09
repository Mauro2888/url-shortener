package com.url.shortener.domain.impl;

import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class UrlHashingCalculator {

    private final Instance<Calculate>calculates;

    @Inject
    public UrlHashingCalculator(Instance<Calculate> calculates) {
        this.calculates = calculates;
    }

    public Url calculateUrl(String url) {
        return calculates.stream()
                .filter(calculate -> calculate.getClass().getSimpleName().toLowerCase().contains("uuid"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No calculator found for url: " + url))
                .extract(url);
    }
}
