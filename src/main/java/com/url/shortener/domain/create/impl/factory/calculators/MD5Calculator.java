package com.url.shortener.domain.create.impl.factory.calculators;

import com.url.shortener.domain.create.impl.factory.AlgoType;
import com.url.shortener.domain.create.impl.factory.Calculate;
import com.url.shortener.domain.create.model.Algorithm;
import com.url.shortener.domain.create.model.HashCode;
import com.url.shortener.domain.create.model.Url;
import common.be.common.core.exception.base.RepositoryException;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@ApplicationScoped
@AlgoType(algorithm = Algorithm.MD5)
public class MD5Calculator implements Calculate {
    @Override
    public CompletionStage<HashCode> generate(String url) {
        try {
            var messageDigest = MessageDigest.getInstance("MD5");
            var digest = messageDigest.digest(url.getBytes());
            var hashedMd5Code = Base64.getEncoder().encodeToString(digest).substring(0, 8).toLowerCase();
            return completedStage(new HashCode(hashedMd5Code));
        } catch (NoSuchAlgorithmException e) {
            throw new RepositoryException(e);
        }
    }
}
