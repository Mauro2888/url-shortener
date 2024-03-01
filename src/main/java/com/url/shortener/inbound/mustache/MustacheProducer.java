package com.url.shortener.inbound.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class MustacheProducer {
    @Produces
    public MustacheFactory produce(){
        return new DefaultMustacheFactory();
    }
}
