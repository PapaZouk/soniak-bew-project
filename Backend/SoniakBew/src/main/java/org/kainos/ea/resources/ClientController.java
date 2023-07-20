package org.kainos.ea.resources;

import io.swagger.annotations.*;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.ClientService;
import org.kainos.ea.cli.Client;
import org.kainos.ea.cli.RequestClientWithMaxValue;
import org.kainos.ea.client.FailedToVerifyTokenException;
import org.kainos.ea.client.TokenExpiredException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Api
@Path("/")
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
public class ClientController {

    private static final String CLIENTS_TAG = "Sales Team";
    private static final String CLIENTS = "clients";
    private static final String MAX_VALUE = "/maxvalue";

    private final ClientService clientService = new ClientService();
    private final AuthService authService = new AuthService();

    @GET
    @Path(CLIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retrieve list of deliveryman employees",
            tags = CLIENTS_TAG,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieve all clients from the database",
                    response = Client.class
            ),
            @ApiResponse(code = 404, message = "Failed to retrieve all clients from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")})

    public Response getAllClients(@QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isSales(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(clientService.getAllClients()).build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path(CLIENTS + MAX_VALUE)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve client with max project`s value", tags = CLIENTS_TAG)
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieve client with max project`s value from the database",
                    response = RequestClientWithMaxValue.class
            ),
            @ApiResponse(code = 404, message = "Failed to retrieve data from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")})

    public Response getClientWithMaxValue(@QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isSales(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(clientService.getClientWithMaxValue()).build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }

    }
}
