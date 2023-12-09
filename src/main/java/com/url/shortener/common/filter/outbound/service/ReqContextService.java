package com.url.shortener.common.filter.outbound.service;

import jakarta.ws.rs.client.ClientRequestContext;

public interface ReqContextService {
    String setup(ClientRequestContext requestContext);
}
