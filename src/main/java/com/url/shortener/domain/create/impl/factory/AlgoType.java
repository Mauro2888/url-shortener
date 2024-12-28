package com.url.shortener.domain.create.impl.factory;

import com.url.shortener.domain.create.model.Algorithm;
import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({PARAMETER, TYPE})
public @interface AlgoType {

    Algorithm algorithm();
}