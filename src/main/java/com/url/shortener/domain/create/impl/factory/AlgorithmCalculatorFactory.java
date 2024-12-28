package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AlgorithmCalculatorFactory {

    private final ConcurrentHashMap<Algorithm,AlgorithmLiteral> cache = new ConcurrentHashMap<>();

    public AlgoType select(Algorithm algorithm) {
        return cache.computeIfAbsent(algorithm, AlgorithmLiteral::of);
    }
}
