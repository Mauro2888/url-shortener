package com.url.shortener.domain.find.repository;

import com.url.shortener.domain.create.model.Url;

import java.util.concurrent.CompletionStage;

public interface UrlShorterFindByLongUrlRepository {
    CompletionStage<Url>find(Url url);
}
