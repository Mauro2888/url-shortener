package com.url.shortener.inbound.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.create.model.UrlBuilder;

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
            return new UrlBuilder()
                    .withShortUrl(writer.toString())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
