package com.url.shortener.common.filter.inbound.impl;


import com.url.shortener.common.filter.inbound.service.ReqInboundContainerCtxService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class HttpInboundRequestServiceImpl implements ReqInboundContainerCtxService {
    private final Logger log = Logger.getLogger(getClass().getName());
    private final Jsonb jsonb = JsonbBuilder.create();

    private String getReqHeaders(ContainerRequestContext ContainerRequestContext) {
        var headers = ContainerRequestContext.getHeaders();
        return headers == null || headers.isEmpty() ? "<none>" : headers.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n "));
    }

    private String getReqInfo(ContainerRequestContext ContainerRequestContext) {
        var uri = ContainerRequestContext.getUriInfo().getRequestUri();
        return uri != null ? uri.toString() : "<none>";
    }

    private String bodyReq(ContainerRequestContext requestContext) {
        var test = Optional.ofNullable(requestContext)
                .filter(ContainerRequestContext::hasEntity)
                .filter(response -> response.getLength() > 0)
                .map(ContainerRequestContext::getMediaType)
                .filter(mediaType -> mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE))
                .isPresent();
        if (test) {
            try {
                var inputStreamBytes = requestContext.getEntityStream().readAllBytes();
                requestContext.setEntityStream(new ByteArrayInputStream(inputStreamBytes));
                return new String(inputStreamBytes);
            } catch (Exception e) {
                log.warning(e.getMessage());
                return "<empty>";
            }
        } else {
            return "<empty>";
        }
    }

    @Override
    public String setup(ContainerRequestContext containerRequestContext) {
        return String.format("Request: %s %s\nReq Headers:\n%s\nReq body:\n%s",
                getReqInfo(containerRequestContext),
                containerRequestContext.getMethod(),
                getReqHeaders(containerRequestContext),
                bodyReq(containerRequestContext));
    }
}
