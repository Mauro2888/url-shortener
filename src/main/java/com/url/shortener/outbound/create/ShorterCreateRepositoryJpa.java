package com.url.shortener.outbound.create;

import com.url.shortener.common.transactional.AsyncRunner;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.repository.UrlShorterCreateRepository;
import com.url.shortener.outbound.create.mapper.ShortUrlEntityMapper;
import com.url.shortener.outbound.find.ShorterFindRepositoryJpa;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.URL_FIND_BY_CODE;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.jboss.logging.Logger.Level.INFO;

@ApplicationScoped
public class ShorterCreateRepositoryJpa implements UrlShorterCreateRepository {

    private final Logger log = Logger.getLogger(getClass().getName());
    @PersistenceContext
    private final EntityManager entityManager;
    private final AsyncRunner asyncRunner;
    private final ShortUrlEntityMapper shortUrlEntityMapper;

    @Inject
    public ShorterCreateRepositoryJpa(EntityManager entityManager,
                                      AsyncRunner asyncRunner,
                                      ShortUrlEntityMapper shortUrlEntityMapper, ShorterFindRepositoryJpa shorterFindRepositoryJpa) {
        this.entityManager = entityManager;
        this.asyncRunner = asyncRunner;
        this.shortUrlEntityMapper = shortUrlEntityMapper;
    }

    @Override
    public CompletionStage<Url> create(Url url) {
        return asyncRunner.supplyAsync(() -> createSync(url));
    }

    private Url createSync(Url url) {
        log.log(INFO, "Creating url %s".formatted(url));
        var notFound = entityManager.createNamedQuery(URL_FIND_BY_CODE, ShortUrlEntity.class)
                .setParameter(CODE, url.code())
                .setMaxResults(1)
                .getResultList()
                .isEmpty();

        if (notFound) {
            var shortUrlEntity = shortUrlEntityMapper.apply(url);
            log.log(INFO, "Persisting %s".formatted(shortUrlEntity));
            entityManager.persist(shortUrlEntity);
        }
        return url;
    }
}
