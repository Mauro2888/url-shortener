package com.url.shortener.common.filter.inbound.service;

import jakarta.ws.rs.container.ContainerResponseContext;

public interface ResInboundContainerCtxService {
    String setup(ContainerResponseContext containerResponseContext);
}
