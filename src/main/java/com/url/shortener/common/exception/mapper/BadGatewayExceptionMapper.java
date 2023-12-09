package
        com.url.shortener.common.exception.mapper;

import com.url.shortener.common.exception.BadGatewayException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadGatewayExceptionMapper implements ExceptionMapper<BadGatewayException> {

    @Override
    public Response toResponse(BadGatewayException e) {
        return Response.status(Response.Status.BAD_GATEWAY)
                .entity(e.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
