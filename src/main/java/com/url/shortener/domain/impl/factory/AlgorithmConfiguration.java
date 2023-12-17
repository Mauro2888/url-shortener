package com.url.shortener.domain.impl.factory;

import com.url.shortener.domain.model.Algorithm;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Provider
public class AlgorithmConfiguration {

    private final Instance<Calculate> instancesCalculator;

    @Inject
    public AlgorithmConfiguration(Instance<Calculate> instancesCalculator) {
        this.instancesCalculator = instancesCalculator;
    }

    public Map<Algorithm, Calculate> services() {
        return instancesCalculator.stream().collect(Collectors.toMap(Calculate::algoType, Function.identity()));
    }
}
