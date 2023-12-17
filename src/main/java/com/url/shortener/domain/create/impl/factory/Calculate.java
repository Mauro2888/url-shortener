package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;

import java.util.concurrent.CompletionStage;

public interface Calculate {
    CompletionStage<Url> generate(String url);

    Algorithm algoType();
}
