package com.url.shortener.inbound.find;

import common.be.common.rest.qualifier.InboundDelegate;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.concurrent.CompletionStage;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/shortener")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@RequestScoped
@Tag(name = "UrlShortener API V1")
public class UrlShorterFindResourceImpl implements UrlShorterFindResource {

    private final UrlShorterFindResource delegate;

    @Inject
    public UrlShorterFindResourceImpl(@InboundDelegate UrlShorterFindResource delegate) {
        this.delegate = delegate;
    }

    @Override
    @GET
    @APIResponse(responseCode = "302", description = "Url found")
    @APIResponse(responseCode = "400", description = "Bad request")
    @APIResponse(responseCode = "500", description = "Internal server error")
    @Operation(
        summary = "Short url code to be find",
        description = """
            <br>This endpoint find url by code"
            """
    )
    public CompletionStage<Response> find(
            @Parameter(
                    name = "code",
                    description = "short url code",
                    required = true,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = SchemaType.STRING, example = "3b4d5f6d")
            )
            @QueryParam("code") @NotNull String code) {
        return delegate.find(code);
    }
}
