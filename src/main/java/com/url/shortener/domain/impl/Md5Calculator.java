package com.url.shortener.domain.impl;

import com.url.shortener.domain.model.Url;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ApplicationScoped
public class Md5Calculator implements Calculate {
    @Override
    public Url extract(String url) {
        try {
            var messageDigest = MessageDigest.getInstance("MD5");
            var digest = messageDigest.digest(url.getBytes());
            var result = Base64.getEncoder().encodeToString(digest).substring(0, 8);
            return new Url(url,"http://tiny.com/".concat(result));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
