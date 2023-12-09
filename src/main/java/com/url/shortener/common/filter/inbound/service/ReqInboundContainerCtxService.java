package com.url.shortener.common.filter.inbound.service;

import jakarta.ws.rs.container.ContainerRequestContext;

public interface ReqInboundContainerCtxService {
    String setup(ContainerRequestContext containerRequestContext);
}
