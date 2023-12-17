package com.url.shortener.domain.impl.factory.calculators;

import com.url.shortener.domain.impl.factory.Calculate;
import com.url.shortener.domain.model.Algorithm;
import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class MD5Calculator implements Calculate {
    @Override
    public CompletionStage<Url> generate(String url) {
        try {
            var messageDigest = MessageDigest.getInstance("MD5");
            var digest = messageDigest.digest(url.getBytes());
            var result = Base64.getEncoder().encodeToString(digest).substring(0, 8).toLowerCase();
            return completedStage(new Url(url,"http://shortner.com/".concat(result)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Algorithm algoType() {
        return Algorithm.MD5;
    }
}