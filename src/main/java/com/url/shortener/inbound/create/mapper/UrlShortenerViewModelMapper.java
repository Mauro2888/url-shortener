package com.url.shortener.inbound.create.mapper;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.vm.UrlShortenerViewModel;
import com.url.shortener.vm.UrlShortenerViewModelBuilder;
import com.url.shortener.vm.UrlViewModel;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

@ApplicationScoped
public class UrlShortenerViewModelMapper implements Function<Url, UrlViewModel> {
    @Override
    public UrlViewModel apply(Url url) {
        return UrlViewModel.builder()
            .withShortUrl(url.shortUrl())
            .withCode(url.code())
            .withOriginalUrl(url.originalUrl())
            .build();
    }
}
