package com.url.shortener.inbound.find;

import jakarta.ws.rs.core.Response;

import java.util.concurrent.CompletionStage;

public interface UrlShorterFindResource {
    CompletionStage<Response> find(String code);
}
