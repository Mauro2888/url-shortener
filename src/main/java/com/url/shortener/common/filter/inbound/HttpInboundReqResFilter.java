package com.url.shortener.common.filter.inbound;

import com.url.shortener.common.filter.inbound.impl.HttpInboundRequestServiceImpl;
import com.url.shortener.common.filter.inbound.impl.HttpInboundResponseServiceImpl;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Priority(9000)
@ApplicationScoped
public class HttpInboundReqResFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final Logger log = Logger.getLogger(getClass().getName());

    private final HttpInboundResponseServiceImpl httpInboundResponseServiceImpl;
    private final HttpInboundRequestServiceImpl httpInboundRequestServiceImpl;

    @Inject
    public HttpInboundReqResFilter(HttpInboundResponseServiceImpl httpInboundResponseServiceImpl,
                                   HttpInboundRequestServiceImpl httpInboundRequestServiceImpl) {
        this.httpInboundResponseServiceImpl = httpInboundResponseServiceImpl;
        this.httpInboundRequestServiceImpl = httpInboundRequestServiceImpl;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        log.log(Level.INFO, httpInboundRequestServiceImpl.setup(containerRequestContext));
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        log.log(Level.INFO, httpInboundResponseServiceImpl.setup(containerResponseContext));
    }
}
