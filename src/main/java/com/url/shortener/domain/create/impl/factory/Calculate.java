package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.HashCode;

import java.util.concurrent.CompletionStage;

public interface Calculate {
    CompletionStage<HashCode> generate(String url);
}
