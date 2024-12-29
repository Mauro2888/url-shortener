package com.url.shortener.inbound.find;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.domain.find.UrlShorterFindService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static java.util.concurrent.CompletableFuture.completedStage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UrlShorterFindDelegateTest {

    private UrlShorterFindDelegate sut;

    @Mock
    private UrlShorterFindService urlShorterFindService;

    @BeforeEach
    void setUp() {
        sut = new UrlShorterFindDelegate(urlShorterFindService);
    }

    @Test
    void shouldFind() {
        //given
        var code = "3afungfe";
        var url = "https://google.com";
        var shortUrl = "https://short.com/".concat(code);
        var expected = Url.builder()
            .withOriginalUrl(url)
            .withCode(code)
            .withShortUrl(shortUrl)
            .build();

        given(urlShorterFindService.find(code))
            .willReturn(completedStage(expected));

        //when
        var result = sut.find(code);

        //then
        var inOrder = Mockito.inOrder(urlShorterFindService);
        inOrder.verify(urlShorterFindService).find(code);
        inOrder.verifyNoMoreInteractions();

        assertThat(result)
            .satisfies(response -> {
                Response actualResponse = response.toCompletableFuture().join();
                assertThat(actualResponse.getStatus()).isEqualTo(Response.Status.FOUND.getStatusCode());
                assertThat(actualResponse.getLocation()).isEqualTo(URI.create(url));
            });
    }
}