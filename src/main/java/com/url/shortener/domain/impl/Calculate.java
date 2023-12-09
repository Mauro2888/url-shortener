package com.url.shortener.domain.impl;

import com.url.shortener.domain.model.Url;

public interface Calculate {
    Url extract(String url);
}
