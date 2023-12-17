package com.url.shortener.domain.find;

import com.url.shortener.domain.create.model.Url;

import java.util.concurrent.CompletionStage;

public interface UrlShorterFindService {
    CompletionStage<Url> find(String code);
}
