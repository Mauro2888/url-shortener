package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;

public record AlgorithmLiteral(Algorithm algorithm) implements AlgoType {

    public static AlgorithmLiteral of(Algorithm algorithm) {
        return new AlgorithmLiteral(algorithm);
    }

    @Override
    public Class<AlgoType> annotationType() {
        return AlgoType.class;
    }
}
