package com.url.shortener.common.filter.inbound.impl;

import com.url.shortener.common.filter.inbound.service.ResInboundContainerCtxService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.container.ContainerResponseContext;

import java.util.stream.Collectors;

@ApplicationScoped
public class HttpInboundResponseServiceImpl implements ResInboundContainerCtxService {

    private final Jsonb jsonb = JsonbBuilder.create();
    private String bodyRes(ContainerResponseContext responseContext) {
        if (responseContext.hasEntity()){
            return "response body ".concat(jsonb.toJson(responseContext.getEntity()));
        }else {
            return "response body <empty>";
        }
    }

    private String getResHeaders(ContainerResponseContext clientResponseContext) {
        var headers = clientResponseContext.getHeaders();
        return headers == null || headers.isEmpty() ? "<none>" : headers.entrySet().stream()
                .filter(entry -> entry.getKey() != null && !entry.getKey().isEmpty()
                        && entry.getValue() != null && !entry.getValue().isEmpty())
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n "));
    }

    @Override
    public String setup(ContainerResponseContext containerResponseContext) {
        return String.format("Response: %s\nRes Headers:\n%s\nRes body:\n%s",
                containerResponseContext.getStatus(),
                getResHeaders(containerResponseContext),
                bodyRes(containerResponseContext));
    }
}
