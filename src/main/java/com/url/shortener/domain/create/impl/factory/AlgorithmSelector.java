package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class AlgorithmSelector {

    private final Instance<Calculate> instancesCalculator;
    private final AlgorithmCalculatorFactory algorithmFactory;

    @Inject
    public AlgorithmSelector(@Any Instance<Calculate> instancesCalculator, AlgorithmCalculatorFactory algorithmFactory) {
        this.instancesCalculator = instancesCalculator;
        this.algorithmFactory = algorithmFactory;
    }

    public Calculate get(Algorithm algorithm) {
        return instancesCalculator.select(algorithmFactory.select(algorithm)).get();
    }
}
