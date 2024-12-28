package com.url.shortener.domain.create.impl.factory.calculators;

import com.url.shortener.domain.create.impl.factory.AlgoType;
import com.url.shortener.domain.create.impl.factory.Calculate;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.Url;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@ApplicationScoped
@AlgoType(algorithm = Algorithm.SHA1)
public class SHA1Calculator implements Calculate {
    @Override
    public Url generate(String url) {
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(url.getBytes(StandardCharsets.UTF_8), 0, url.length());
            var sha1 = DatatypeConverter.printHexBinary(msdDigest.digest()) .substring(0, 8).toLowerCase();
            return Url.builder()
                    .withCode(sha1)
                    .withOriginalUrl(url)
                    .withShortUrl("http://shortner.com/".concat(sha1))
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
