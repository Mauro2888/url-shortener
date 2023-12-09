package com.url.shortener.resource;

import com.url.shortener.common.qualifier.Delegate;
import com.url.shortener.domain.model.Url;
import com.url.shortener.vm.UrlShortenerViewModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.concurrent.CompletionStage;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Path("api/v1/shortener")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UrlShortenerCreateResourceImpl implements UrlShortenerCreateResource {

    private final UrlShortenerCreateResource delegate;

    @Inject
    public UrlShortenerCreateResourceImpl(@Delegate UrlShortenerCreateResource delegate) {
        this.delegate = delegate;
    }

    @Override
    @POST
    public CompletionStage<Url> create(UrlShortenerViewModel urlShortenerViewModel) {
        return delegate.create(urlShortenerViewModel);
    }
}
