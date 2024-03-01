package com.url.shortener.inbound.mustache;


import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class MustacheExecutor {

    public String execute(Mustache mustache, Map templateValues){
        return mustache.execute(new StringWriter(), templateValues).toString();
    }
}
