package com.url.shortener.domain.find.impl;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.UrlShorterFindService;
import com.url.shortener.domain.find.repository.UrlShorterFindByCodeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UrlShorterFindServiceImpl implements UrlShorterFindService {
    private final UrlShorterFindByCodeRepository urlShorterFindByCodeRepository;

    @Inject
    public UrlShorterFindServiceImpl(UrlShorterFindByCodeRepository urlShorterFindByCodeRepository) {
        this.urlShorterFindByCodeRepository = urlShorterFindByCodeRepository;
    }

    @Override
    public CompletionStage<Url> find(String code) {
        return urlShorterFindByCodeRepository.find(code);
    }
}
