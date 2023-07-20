package org.kainos.ea.resources;

import io.swagger.annotations.*;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Login;
import org.kainos.ea.cli.LoginNoRole;
import org.kainos.ea.client.FailedToCheckIfUserIsRegisteredException;
import org.kainos.ea.client.FailedToGenerateTokenException;
import org.kainos.ea.client.FailedToLoginException;
import org.kainos.ea.client.FailedToRegisterNewUserException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.kainos.ea.resources.AuthController.*;

@Api
@Path(API)
@SwaggerDefinition(
        info = @Info(
                title = "Soniak Bew API",
                version = "1.0.0",
                license = @License(name = "Kainos.com", url = "https://www.kainos.com/"),
                contact = @Contact(
                        name = "Martyna Świerszcz, Oleksandr Gneushev, Rafał Papała, Paweł Skóra",
                        url = "https://github.com/PapaZouk/soniak-bew-project.git",
                        email = "martyna.swierszcz@kainos.com, oleksandr.gneushev@kainos.com," +
                                " rafal.papala@kainos.com, pawel.skora@kainos.com"),
                description = "Soniak Bew API that provides access for Management Team, HR Team and Sales Team " +
                        "necessary endpoints for each department to have access to required data. To provide fully " +
                        "secure environment, each user should successfully log in to the service and use provided " +
                        "token to access available endpoint."))
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
    public Response login(LoginNoRole login) {
        try {
            return Response.ok(authService.login(login)).build();
        } catch (FailedToLoginException | FailedToGenerateTokenException e) {
           return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path(REGISTER)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Register new user to the service", tags = AUTHORIZATION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered new user"),
            @ApiResponse(code = 400, message = "Failed to register new user"),
            @ApiResponse(code = 500, message = "Failed to connect with the server")
    })
    @ResponseHeader()
    public Response registerUser(Login login) {
        try {
            authService.registerUser(login);
            return Response.ok().build();
        } catch (FailedToCheckIfUserIsRegisteredException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (FailedToRegisterNewUserException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
