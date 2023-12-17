package com.url.shortener.resource.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.StringWriter;


public class MustacheFactory {

    private final String template;

    public MustacheFactory(String template) {
        super();
        this.template = template;
    }

    public Url create(String url) {
        try {
            var compile = new DefaultMustacheFactory().compile(template);
            if (compile == null) throw new IllegalArgumentException();
            var writer = new StringWriter();
            compile.execute(writer, url).flush();
            return Url.builder()
                    .shortUrl(writer.toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
