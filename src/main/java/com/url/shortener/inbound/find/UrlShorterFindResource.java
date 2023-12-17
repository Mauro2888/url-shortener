package com.url.shortener.inbound.find;

import com.url.shortener.vm.UrlShortenerViewModel;

import java.util.concurrent.CompletionStage;

public interface UrlShorterFindResource {
    CompletionStage<UrlShortenerViewModel> find(String code);
}
