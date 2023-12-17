package com.url.shortener.domain.impl.factory.calculators;

import com.url.shortener.domain.impl.factory.Calculate;
import com.url.shortener.domain.model.Algorithm;
import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
public class SHA1Calculator implements Calculate {
    @Override
    public CompletionStage<Url> generate(String url) {
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(url.getBytes(StandardCharsets.UTF_8), 0, url.length());
            var sha1 = DatatypeConverter.printHexBinary(msdDigest.digest()) .substring(0, 8).toLowerCase();
            return completedStage(new Url(url, "http://shortner.com/".concat(sha1)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Algorithm algoType() {
        return Algorithm.SHA1;
    }
}
