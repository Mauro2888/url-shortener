package com.url.shortener.domain.find.impl;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.repository.UrlShorterFindByCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.concurrent.CompletableFuture.completedStage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UrlShorterFindServiceImplTest {

    private UrlShorterFindServiceImpl sut;

    @Mock
    private UrlShorterFindByCodeRepository urlShorterFindByCodeRepository;

    @BeforeEach
    void setUp() {
        sut = new UrlShorterFindServiceImpl(urlShorterFindByCodeRepository);
    }

    @Test
    void shouldFindCode() {
        //given
        var code = "3afungfe";
        var url = "https://google.com";
        var shortUrl = "https://short.com/".concat(code);
        var expected = Url.builder()
            .withOriginalUrl(url)
            .withCode(code)
            .withShortUrl(shortUrl)
            .build();
        given(urlShorterFindByCodeRepository.find(code))
            .willReturn(completedStage(expected));

        //when
        var result = sut.find(code);

        //then
        assertThat(result).isCompletedWithValue(expected);
    }
}