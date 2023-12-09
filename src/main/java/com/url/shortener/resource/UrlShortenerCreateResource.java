package com.url.shortener.resource;

import com.url.shortener.domain.model.Url;
import com.url.shortener.vm.UrlShortenerViewModel;

import java.util.concurrent.CompletionStage;

public interface UrlShortenerCreateResource {
    CompletionStage<Url> create(UrlShortenerViewModel urlShortenerViewModel);
}
