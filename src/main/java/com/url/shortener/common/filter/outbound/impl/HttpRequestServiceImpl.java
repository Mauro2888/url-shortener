package com.url.shortener.common.filter.outbound.impl;

import com.url.shortener.common.filter.outbound.service.ReqContextService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.ClientRequestContext;

import java.util.stream.Collectors;

@ApplicationScoped
public class HttpRequestServiceImpl implements ReqContextService {
    private final Jsonb jsonb = JsonbBuilder.create();
    @Override
    public String setup(ClientRequestContext requestContext) {
        return String.format("Request: %s %s\nReq Headers:\n%s\nReq body:\n%s\nQuery:\n%s",
                getUri(requestContext),
                requestContext.getMethod(),
                getReqHeaders(requestContext),
                bodyReq(requestContext),
                getQuery(requestContext));
    }

    private String getReqHeaders(ClientRequestContext clientRequestContext) {
        var headers = clientRequestContext.getHeaders();
        return headers == null || headers.isEmpty() ? "<none>" : headers.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n "));
    }

    private String getUri(ClientRequestContext clientRequestContext) {
        var uri = clientRequestContext.getUri();
        return uri != null ? uri.toString() : "<none>";
    }

    private String getQuery(ClientRequestContext clientRequestContext) {
        String query = clientRequestContext.getUri().getQuery();
        return (query != null) ? String.join("\n", query.split("&")) : "<none>";
    }

    private String bodyReq(ClientRequestContext requestContext) {
        var entity = requestContext.getEntity();
        return entity != null ? jsonb.toJson(entity) : "<empty>";
    }
}
