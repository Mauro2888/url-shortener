package com.url.shortener.inbound.find;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.concurrent.CompletableFuture.completedStage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UrlShorterFindResourceImplTest {

    private UrlShorterFindResourceImpl sut;

    @Mock
    private UrlShorterFindDelegate urlShorterFindDelegate;

    @Mock
    private Response response;

    @BeforeEach
    void setUp() {
        sut = new UrlShorterFindResourceImpl(urlShorterFindDelegate);
    }

    @Test
    void shouldReturnShorterUrl() {

        //given

        var code = "3afungfe";
        given(urlShorterFindDelegate.find(code)).willReturn(completedStage(response));

        //when
        var result = sut.find(code);

        //then
        assertThat(result).isCompletedWithValue(response);
        verifyNoInteractions(urlShorterFindDelegate);
    }

}