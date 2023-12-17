package com.url.shortener.outbound.create;

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
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.concurrent.CompletionStage;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.URL_FIND_BY_CODE;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@ApplicationScoped
public class ShorterCreateRepositoryJpa implements UrlShorterCreateRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ManagedExecutor executor;
    private final ShortUrlEntityMapper shortUrlEntityMapper;

    @Inject
    public ShorterCreateRepositoryJpa(EntityManager entityManager,
                                      ManagedExecutor executor,
                                      ShortUrlEntityMapper shortUrlEntityMapper, ShorterFindRepositoryJpa shorterFindRepositoryJpa) {
        this.entityManager = entityManager;
        this.executor = executor;
        this.shortUrlEntityMapper = shortUrlEntityMapper;
    }

    @Override
    @Transactional
    public CompletionStage<Url> create(Url url) {
        return supplyAsync(() -> createSync(url), executor);
    }
    private Url createSync(Url url) {
        var notFound = entityManager.createNamedQuery(URL_FIND_BY_CODE, ShortUrlEntity.class)
                .setParameter(CODE, url.code())
                .setMaxResults(1)
                .getResultList()
                .isEmpty();

        if (notFound) {
            var shortUrlEntity = shortUrlEntityMapper.apply(url);
            entityManager.persist(shortUrlEntity);
        }
        return url;
    }
}
