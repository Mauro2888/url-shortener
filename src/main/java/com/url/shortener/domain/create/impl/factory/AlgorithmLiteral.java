package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.lang.annotation.Annotation;


public class AlgorithmLiteral implements AlgoType {

    private final Algorithm algorithm;

    @Inject
    public AlgorithmLiteral(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public static AlgorithmLiteral of(Algorithm algorithm) {
        return new AlgorithmLiteral(algorithm);
    }

    @Override
    public Algorithm algorithm() {
        return algorithm;
    }

    @Override
    public Class<AlgoType> annotationType() {
        return AlgoType.class;
    }
}
