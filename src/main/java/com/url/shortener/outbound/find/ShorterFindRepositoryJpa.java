package com.url.shortener.outbound.find;

import com.url.shortener.common.exception.NotFoundException;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindRepository;
import com.url.shortener.outbound.jpa.ShortUrlEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static com.url.shortener.outbound.jpa.QueryParameters.CODE;
import static com.url.shortener.outbound.jpa.ShortUrlEntity.URL_FIND_BY_CODE;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@ApplicationScoped
public class ShorterFindRepositoryJpa implements UrlShorterFindRepository {

    private final EntityManager entityManager;
    private final ManagedExecutor executor;

    @Inject
    public ShorterFindRepositoryJpa(EntityManager entityManager, ManagedExecutor executor) {
        this.entityManager = entityManager;
        this.executor = executor;
    }

    @Override
    @Transactional
    public CompletionStage<Url> find(String code) {
        return supplyAsync(() -> findSync(code), executor);
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
