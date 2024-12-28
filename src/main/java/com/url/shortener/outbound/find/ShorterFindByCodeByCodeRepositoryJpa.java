package com.url.shortener.outbound.find;


import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindByCodeRepository;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import common.be.common.core.exception.repository.RepositoryNotFoundException;
import common.be.common.jpa.transactional.AsyncTransactionRunner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.FIND_URL_BY_CODE;

@ApplicationScoped
public class ShorterFindByCodeByCodeRepositoryJpa implements UrlShorterFindByCodeRepository {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final EntityManager entityManager;
    private final AsyncTransactionRunner executor;

    @Inject
    public ShorterFindByCodeByCodeRepositoryJpa(EntityManager entityManager, AsyncTransactionRunner executor) {
        this.entityManager = entityManager;
        this.executor = executor;
    }

    @Override
    public CompletionStage<Url> find(String code) {
        log.info(()-> "try to find url by code %s ".formatted(code));
        return executor.supplyAsync(() -> findSync(code))
                .thenApply(result -> Url.builder()
                        .withOriginalUrl(result.getOriginalUrl())
                        .build());
    }

    public ShortUrlEntity findSync(String code) {
        return entityManager.createNamedQuery(FIND_URL_BY_CODE, ShortUrlEntity.class)
                .setParameter(CODE, code)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new RepositoryNotFoundException("No url found"));
    }
}
