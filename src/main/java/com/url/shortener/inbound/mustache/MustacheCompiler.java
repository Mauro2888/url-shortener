package com.url.shortener.inbound.mustache;


import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.StringReader;
import java.util.UUID;

@ApplicationScoped
public class MustacheCompiler {

    private final MustacheFactory mustacheFactory;

    @Inject
    public MustacheCompiler(MustacheFactory mustacheFactory) {
        this.mustacheFactory = mustacheFactory;
    }

    public Mustache compile(String template){
        return mustacheFactory.compile(new StringReader(template), UUID.randomUUID().toString());
    }
}
