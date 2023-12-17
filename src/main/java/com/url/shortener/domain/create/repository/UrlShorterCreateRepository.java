package com.url.shortener.domain.create.repository;

import com.url.shortener.domain.create.model.Url;

import java.util.concurrent.CompletionStage;

public interface UrlShorterCreateRepository {
    CompletionStage<Url>create(Url url);
}
