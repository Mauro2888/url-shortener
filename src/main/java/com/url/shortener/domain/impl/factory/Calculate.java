package com.url.shortener.domain.impl.factory;

import com.url.shortener.domain.model.Algorithm;
import com.url.shortener.domain.model.Url;

import java.util.concurrent.CompletionStage;

public interface Calculate {
    CompletionStage<Url> generate(String url);

    Algorithm algoType();
}
