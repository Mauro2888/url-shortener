package com.url.shortener.common.filter.outbound.impl;

import com.url.shortener.common.filter.outbound.service.ResContextService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.ClientResponseContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@ApplicationScoped
public class HttpResponseServiceImpl implements ResContextService {
    @Override
    public String setup(ClientResponseContext responseContext) {
        return String.format("Response: %s\nRes Headers:\n%s\nRes body:\n%s",
                responseContext.getStatus(),
                getResHeaders(responseContext),
                bodyRes(responseContext));
    }


    private String bodyRes(ClientResponseContext responseContext) {
        var entityStream = responseContext.getEntityStream();

        if (entityStream == null) {
            return "<empty>";
        }

        try {
            var content = readEntityStream(entityStream);
            responseContext.setEntityStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "<error reading body>";
        }
    }

    private String readEntityStream(InputStream entityStream) throws IOException {
        var buffer = new ByteArrayOutputStream();
        entityStream.transferTo(buffer);
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private String getResHeaders(ClientResponseContext clientResponseContext) {
        var headers = clientResponseContext.getHeaders();
        return headers == null || headers.isEmpty() ? "<none>" : headers.entrySet().stream()
                .filter(entry -> entry.getKey() != null && !entry.getKey().isEmpty()
                        && entry.getValue() != null && !entry.getValue().isEmpty())
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n "));
    }
}
