package com.url.shortener.common.filter.outbound;

import com.url.shortener.common.filter.outbound.impl.HttpRequestServiceImpl;
import com.url.shortener.common.filter.outbound.impl.HttpResponseServiceImpl;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Priority(9000)
@ApplicationScoped
public class HttpReqResFilter implements ClientRequestFilter, ClientResponseFilter {
    private final Logger log = Logger.getLogger(getClass().getName());

    private final HttpResponseServiceImpl httpResponseServiceImpl;
    private final HttpRequestServiceImpl httpRequestServiceImpl;

    @Inject
    public HttpReqResFilter(HttpResponseServiceImpl httpResponseServiceImpl,
                            HttpRequestServiceImpl httpRequestServiceImpl) {
        this.httpResponseServiceImpl = httpResponseServiceImpl;
        this.httpRequestServiceImpl = httpRequestServiceImpl;
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        log.log(Level.INFO, httpResponseServiceImpl.setup(clientResponseContext));
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        log.log(Level.INFO, httpRequestServiceImpl.setup(clientRequestContext));
    }
}
