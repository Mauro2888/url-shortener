package com.url.shortener.domain.find.impl;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.UrlShorterFindService;
import com.url.shortener.domain.find.repository.UrlShorterFindRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UrlShorterFindServiceImpl implements UrlShorterFindService {
    private final UrlShorterFindRepository urlShorterFindRepository;

    @Inject
    public UrlShorterFindServiceImpl(UrlShorterFindRepository urlShorterFindRepository) {
        this.urlShorterFindRepository = urlShorterFindRepository;
    }

    @Override
    public CompletionStage<Url> find(String code) {
        return urlShorterFindRepository.find(code);
    }
}
