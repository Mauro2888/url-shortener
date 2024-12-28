package com.url.shortener.inbound.create;

import com.url.shortener.domain.create.model.Url;
import com.url.shortener.vm.UrlShortenerViewModel;
import common.be.common.rest.qualifier.InboundDelegate;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.concurrent.CompletionStage;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/shortener")
@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Tag(name = "UrlShortener API V1")
public class UrlShortenerCreateResourceImpl implements UrlShortenerCreateResource {

    private final UrlShortenerCreateResource delegate;

    @Inject
    public UrlShortenerCreateResourceImpl(@InboundDelegate UrlShortenerCreateResource delegate) {
        this.delegate = delegate;
    }

    @Override
    @POST
    @APIResponse(responseCode = "201", description = "Url created", content = @Content(schema = @Schema(implementation = Url.class)))
    @APIResponse(responseCode = "400", description = "Bad request")
    @APIResponse(responseCode = "500", description = "Internal server error")
    @Operation(
        summary = "Url to be shortened",
        description = """
            <br>This endpoint generate shortened url.
            """
    )
    public CompletionStage<Url> create(
            @RequestBody(
                    description = "Url to be shortened",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UrlShortenerViewModel.class))
            ) @Valid UrlShortenerViewModel urlShortenerViewModel) {
        return delegate.create(urlShortenerViewModel);

    }
}
