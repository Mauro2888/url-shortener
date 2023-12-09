package com.url.shortener.domain.impl;

import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UUIDCalculator implements Calculate {
    @Override
    public Url extract(String url) {
        var result = UUID.randomUUID().toString().replaceAll("-", "")
                .substring(0, 8);
        return new Url(url, "http://tiny.com/".concat(result));
    }
}
