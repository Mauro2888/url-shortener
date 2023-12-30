package com.url.shortener.outbound.find;


import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindRepository;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import common.exception.NotFoundException;
import common.logger.Log;
import common.transactional.AsyncRunner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.URL_FIND_BY_CODE;

@ApplicationScoped
@Log
public class ShorterFindRepositoryJpa implements UrlShorterFindRepository {

    private final EntityManager entityManager;
    private final AsyncRunner executor;

    @Inject
    public ShorterFindRepositoryJpa(EntityManager entityManager, AsyncRunner executor) {
        this.entityManager = entityManager;
        this.executor = executor;
    }

    @Override
    public CompletionStage<Url> find(String code) {
        return executor.supplyAsync(() -> findSync(code));
    }

    public Url findSync(String code) {
        var query = Optional.ofNullable(entityManager.createNamedQuery(URL_FIND_BY_CODE, ShortUrlEntity.class)
                        .setParameter(CODE, code)
                        .getSingleResult())
                .orElseThrow(() -> new NotFoundException("Not found"));
        return Url.builder()
                .withOriginalUrl(query.getOriginalUrl())
                .build();
    }
}
