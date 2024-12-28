package com.url.shortener.outbound.create;


import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.repository.UrlShorterCreateRepository;
import com.url.shortener.outbound.create.mapper.ShortUrlEntityMapper;
import com.url.shortener.outbound.find.ShorterFindRepositoryJpa;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import common.be.common.jpa.transactional.AsyncTransactionRunner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.URL_FIND_BY_CODE;
import static org.jboss.logging.Logger.Level.INFO;

@ApplicationScoped
public class ShorterCreateRepositoryJpa implements UrlShorterCreateRepository {

    private final Logger log = Logger.getLogger(getClass().getName());

    private final EntityManager entityManager;
    private final AsyncTransactionRunner transactionRunner;
    private final ShortUrlEntityMapper shortUrlEntityMapper;

    @Inject
    public ShorterCreateRepositoryJpa(EntityManager entityManager,
        AsyncTransactionRunner transactionRunner,
                                      ShortUrlEntityMapper shortUrlEntityMapper, ShorterFindRepositoryJpa shorterFindRepositoryJpa) {
        this.entityManager = entityManager;
        this.transactionRunner = transactionRunner;
        this.shortUrlEntityMapper = shortUrlEntityMapper;
    }

    @Override
    public CompletionStage<Url> create(Url url) {
        return transactionRunner.supplyAsync(() -> createSync(url));
    }

    private Url createSync(Url url) {
        log.log(INFO, "Creating url %s".formatted(url));
        var existingUrl = entityManager.createNamedQuery(URL_FIND_BY_CODE, ShortUrlEntity.class)
                .setParameter(CODE, url.code())
                .getResultStream()
                .findFirst();

        existingUrl.ifPresentOrElse(
                existing -> log.log(INFO, "Url %s already exists".formatted(url)),
                () -> {
                    var shortUrlEntity = shortUrlEntityMapper.apply(url);
                    log.log(INFO, "Persisting %s".formatted(shortUrlEntity.getShortUrl()));
                    entityManager.persist(shortUrlEntity);
                }
        );

        return url;
    }
}
