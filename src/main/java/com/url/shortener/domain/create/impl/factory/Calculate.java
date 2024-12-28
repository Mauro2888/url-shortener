package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Url;

public interface Calculate {
    Url generate(String url);
}
