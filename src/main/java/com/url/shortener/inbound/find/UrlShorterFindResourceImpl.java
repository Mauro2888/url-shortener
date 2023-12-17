package com.url.shortener.inbound.find;

import com.url.shortener.common.qualifier.Delegate;
import com.url.shortener.vm.UrlShortenerViewModel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.concurrent.CompletionStage;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/shortener")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@RequestScoped
public class UrlShorterFindResourceImpl implements UrlShorterFindResource {

    private final UrlShorterFindResource delegate;

    @Inject
    public UrlShorterFindResourceImpl(@Delegate UrlShorterFindResource delegate) {
        this.delegate = delegate;
    }

    @Override
    @GET
    @APIResponse(responseCode = "200", description = "Url found")
    @APIResponse(responseCode = "400", description = "Bad request")
    @APIResponse(responseCode = "500", description = "Internal server error")
    public CompletionStage<UrlShortenerViewModel> find(
            @Parameter(
                    name = "code",
                    description = "short url code",
                    required = true,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = SchemaType.STRING, example = "3b4d5f6d")
            )

            @QueryParam("code")
           String code) {
        return delegate.find(code);
    }
}
