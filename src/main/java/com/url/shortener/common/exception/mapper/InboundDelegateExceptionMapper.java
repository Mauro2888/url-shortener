package com.url.shortener.common.exception.mapper;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.Providers;

import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class InboundDelegateExceptionMapper implements ExceptionMapper<CompletionException> {
    @Context
    private Providers provider;

    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    public Response toResponse(CompletionException e) {
        var throwableCause = e.getCause();
        if (throwableCause != null && provider != null) {
            @SuppressWarnings("unchecked")
            var exceptionMapper = (ExceptionMapper<Throwable>) provider.getExceptionMapper((Class) throwableCause.getClass());
            if (exceptionMapper != null) {
                log.log(Level.INFO,() -> "Delegating to exception mapper class: %s".formatted(exceptionMapper.getClass().getName()));
                return exceptionMapper.toResponse(throwableCause);
            }
        }

        log.log(Level.WARNING, e::getMessage);
        return generate(e, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    private Response generate(Throwable throwable, int status) {
        var errors = Map.of("error", throwable.getMessage());
        return Response.status(status)
                .entity(errors)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }
}

