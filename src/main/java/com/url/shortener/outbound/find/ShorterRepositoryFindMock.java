package com.url.shortener.outbound.find;

import com.url.shortener.common.exception.NotFoundException;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class ShorterRepositoryFindMock implements UrlShorterFindRepository {
    @Override
    public CompletionStage<Url> find(String code) {
        Map<String, String> mockDb = new HashMap<>();
        mockDb.put("3b4d5f6d", "http://www.google.it");
        var url = mockDb.getOrDefault(code, null);
        if (url == null) throw new NotFoundException("Not found");
        return completedStage(Url.builder()
                .originalUrl(url)
                .build());
    }
}
