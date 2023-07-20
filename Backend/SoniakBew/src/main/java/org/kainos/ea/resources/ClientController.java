package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.ClientService;
import org.kainos.ea.cli.Client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;



@Api
@Path("/")
public class ClientController {

    private static final String CLIENTS_TAG = "Sales Team";
    private static final String CLIENTS = "clients";
    private static final String MAX_VALUE = "/maxvalue";

    private final ClientService clientService = new ClientService();
    private final AuthService authService = new AuthService();

    @GET
    @Path(CLIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve list of deliveryman employees", tags = CLIENTS_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all clients from " +
                    "the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve all clients from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")})

    public Response getAllClients() {
        try {
            return Response.ok(clientService.getAllClients()).build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(CLIENTS + MAX_VALUE)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve client with max project`s value", tags = CLIENTS_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve client with max project`s " +
                    "value from " +
                    "the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve data from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")})

    public Response getClientWithMaxValue() {
        try {
            return Response.ok(clientService.getClientWithMaxValue()).build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
