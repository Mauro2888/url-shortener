package com.url.shortener.inbound.find.mapper;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.vm.UrlShortenerViewModel;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

@ApplicationScoped
public class UrlShorterMapper implements Function<Url, UrlShortenerViewModel> {
    @Override
    public UrlShortenerViewModel apply(Url url) {
        return UrlShortenerViewModel.builder()
                .withOriginalUrl(url.originalUrl())
                .build();
    }
}
