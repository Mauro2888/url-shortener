package com.url.shortener.outbound.find;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindByLongUrlRepository;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import common.be.common.core.exception.repository.RepositoryNotFoundException;
import common.be.common.jpa.transactional.AsyncTransactionRunner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.QueryParameters.LONG_URL;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.FIND_CODE_BY_LONG_URL;

@ApplicationScoped
public class ShorterFindByLongUrlByCodeRepositoryJpa implements UrlShorterFindByLongUrlRepository {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final EntityManager entityManager;
    private final AsyncTransactionRunner executor;

    @Inject
    public ShorterFindByLongUrlByCodeRepositoryJpa(EntityManager entityManager, AsyncTransactionRunner executor) {
        this.entityManager = entityManager;
        this.executor = executor;
    }

    @Override
    public CompletionStage<Url> find(Url url) {
        log.info(() -> "try to find url by long url %s ".formatted(url.originalUrl()));
        return executor.supplyAsync(() -> findSync(url.originalUrl()))
            .thenApply(result -> Url.builder()
                .withOriginalUrl(result.getCode())
                .build());
    }

    public ShortUrlEntity findSync(String url) {
        return entityManager.createNamedQuery(FIND_CODE_BY_LONG_URL, ShortUrlEntity.class)
            .setParameter(LONG_URL, url)
            .getResultStream()
            .findFirst()
            .orElseThrow(() -> new RepositoryNotFoundException("No url found"));
    }
}
