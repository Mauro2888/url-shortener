package com.url.shortener.outbound.find;


import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindRepository;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import common.be.common.core.exception.repository.RepositoryNotFoundException;
import common.be.common.jpa.transactional.AsyncTransactionRunner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.concurrent.CompletionStage;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.URL_FIND_BY_CODE;

@ApplicationScoped
public class ShorterFindRepositoryJpa implements UrlShorterFindRepository {

    private final EntityManager entityManager;
    private final AsyncTransactionRunner executor;

    @Inject
    public ShorterFindRepositoryJpa(EntityManager entityManager, AsyncTransactionRunner executor) {
        this.entityManager = entityManager;
        this.executor = executor;
    }

    @Override
    public CompletionStage<Url> find(String code) {
        return executor.supplyAsync(() -> findSync(code))
                .thenApply(result -> Url.builder()
                        .withOriginalUrl(result.getOriginalUrl())
                        .build());
    }

    public ShortUrlEntity findSync(String code) {
        return entityManager.createNamedQuery(URL_FIND_BY_CODE, ShortUrlEntity.class)
                .setParameter(CODE, code)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new RepositoryNotFoundException("Not found"));
    }
}
