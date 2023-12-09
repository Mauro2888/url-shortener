package com.url.shortener.common.trace;

import io.opentelemetry.api.trace.Span;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

@Provider
public class HttpTraceIdRes implements ContainerResponseFilter {
    private final Logger log = Logger.getLogger(getClass().getName());


    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        try {
            var traceId = Span.current().getSpanContext().getTraceId();
            containerRequestContext.getHeaders().add("traceId", traceId);
            log.log(INFO, () -> "traceId: %s".formatted(traceId));
        } catch (Exception e) {
            log.log(WARNING, "Error getting traceId", e);
        }
    }
}
