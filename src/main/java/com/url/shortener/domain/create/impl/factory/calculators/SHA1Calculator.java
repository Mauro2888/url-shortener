package com.url.shortener.domain.create.impl.factory.calculators;

import com.url.shortener.domain.create.impl.factory.AlgoType;
import com.url.shortener.domain.create.impl.factory.Calculate;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.HashCode;
import com.url.shortener.domain.create.model.Url;
import common.be.common.core.exception.base.RepositoryException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
@AlgoType(algorithm = Algorithm.SHA1)
public class SHA1Calculator implements Calculate {
    @Override
    public CompletionStage<HashCode> generate(String url) {
        try {
            var msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(url.getBytes(StandardCharsets.UTF_8), 0, url.length());
            var sha1 = DatatypeConverter.printHexBinary(msdDigest.digest()) .substring(0, 8).toLowerCase();
            return completedStage(new HashCode(sha1));
        } catch (NoSuchAlgorithmException e) {
            throw new RepositoryException(e);
        }
    }
}
