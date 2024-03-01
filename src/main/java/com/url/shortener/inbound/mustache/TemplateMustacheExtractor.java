package com.url.shortener.inbound.mustache;

import com.github.mustachejava.Code;
import com.github.mustachejava.codes.ValueCode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TemplateMustacheExtractor {

    private final MustacheCompiler mustacheCompiler;

    @Inject
    public TemplateMustacheExtractor(MustacheCompiler mustacheCompiler) {
        this.mustacheCompiler = mustacheCompiler;
    }


    public Set getVariables(String template){
        var variables =  mustacheCompiler.compile(template);
        return Arrays.stream(variables.getCodes())
                .filter(ValueCode.class::isInstance)
                .map(Code::getName)
                .collect(Collectors.toSet());
    }
}
