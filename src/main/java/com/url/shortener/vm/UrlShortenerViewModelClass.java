package com.url.shortener.vm;

import com.url.shortener.common.builder.Builder;


@Builder
public class UrlShortenerViewModelClass{

    private String originalUrl;
    private AlgorithmViewModel algorithmViewModel;

    public UrlShortenerViewModelClass(String originalUrl, AlgorithmViewModel algorithmViewModel) {
        this.originalUrl = originalUrl;
        this.algorithmViewModel = algorithmViewModel;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public AlgorithmViewModel getAlgorithmViewModel() {
        return algorithmViewModel;
    }

    public void setAlgorithmViewModel(AlgorithmViewModel algorithmViewModel) {
        this.algorithmViewModel = algorithmViewModel;
    }
}
