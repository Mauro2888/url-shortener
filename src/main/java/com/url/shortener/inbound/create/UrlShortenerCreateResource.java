package com.url.shortener.inbound.create;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.vm.UrlShortenerViewModel;
import com.url.shortener.vm.UrlViewModel;

import java.util.concurrent.CompletionStage;

public interface UrlShortenerCreateResource {
    CompletionStage<UrlViewModel> create(UrlShortenerViewModel urlShortenerViewModel);
}
