package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Login;
import org.kainos.ea.client.FailedToGenerateTokenException;
import org.kainos.ea.client.FailedToLoginException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.kainos.ea.resources.AuthController.*;

@Api
@Path(API)
public class AuthController {
    public static final String API = "/api";
    private static final String AUTHORIZATION = "Authorization Panel";
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";

    private final AuthService authService = new AuthService();

    @POST
    @Path(LOGIN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Logs in user with the given credentials", tags = AUTHORIZATION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully connected"),
            @ApiResponse(code = 400, message = "Failed to log in"),
            @ApiResponse(code = 500, message = "Failed to connect with the server")
    })
    public Response login(Login login) {
        try {
            return Response.ok(authService.login(login)).build();
        } catch (FailedToLoginException | FailedToGenerateTokenException e) {
           return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}