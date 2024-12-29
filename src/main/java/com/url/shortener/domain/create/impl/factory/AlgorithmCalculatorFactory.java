package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;

public interface AlgorithmCalculatorFactory {
    AlgoType select(Algorithm algorithm);
}
