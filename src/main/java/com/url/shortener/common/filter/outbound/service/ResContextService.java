package com.url.shortener.common.filter.outbound.service;

import jakarta.ws.rs.client.ClientResponseContext;

public interface ResContextService {
    String setup(ClientResponseContext responseContext);
}
