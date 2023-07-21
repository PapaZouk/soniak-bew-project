package org.kainos.ea.resources;

import io.swagger.annotations.*;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.EmployeeService;
import org.kainos.ea.cli.DeliveryEmployee;
import org.kainos.ea.cli.SalesEmployee;
import org.kainos.ea.client.*;

import javax.ws.rs.*;
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
public class EmployeeController {
    private static final String HR_TEAM_TAG = "HR Team";
    private static final String EMPLOYEES = "employees";
    private static final String SALESMAN = "/salesman";
    private static final String DELIVERYMAN = "/deliveryman";

    private static final String CREATE = "/create";
    private static final String UPDATE = "/update";
    public static final String ID = "/{id}";
    private static final String DELETE = "/delete";

    private final EmployeeService employeeService = new EmployeeService();
    @GET
    @Path(EMPLOYEES + DELIVERYMAN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retrieve list of deliveryman employees",
            tags = HR_TEAM_TAG,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all delivery employees from the database",
                    response = DeliveryEmployee.class),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Failed to retrieve all delivery employees from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response getAllDeliveryEmployees(@QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.getAllDeliveryEmployees()).build();
        } catch (FailedToGetAllDeliverymanEmployeesException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    private final AuthService authService = new AuthService();

    @POST
    @Path(EMPLOYEES + DELIVERYMAN + CREATE)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiModelProperty(dataType = MediaType.APPLICATION_JSON, required = true)
    @ApiOperation(value = "Creates new delivery employee", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added new delivery employee to the database"),
            @ApiResponse(code = 400, message = "Failed to add new delivery employee to the database"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response createNewDeliveryman(
            @ApiParam(
                    value = "Deliveryman details",
                    required = true,
                    type = MediaType.APPLICATION_JSON
            ) DeliveryEmployee deliveryEmployee,
            @QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.createNewDeliverymanEmployee(deliveryEmployee)).build();
        } catch (FailedToCreateNewDeliverymanEmployeeException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path(EMPLOYEES + DELIVERYMAN + ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retrieve deliveryman employee by the given ID number",
            tags = HR_TEAM_TAG,
            response = DeliveryEmployee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved delivery employee by the given ID number",
                    response = DeliveryEmployee.class),
            @ApiResponse(code = 404, message = "Failed to retrieve delivery employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response deliverymanById(
            @ApiParam(
                    value = "ID of deliveryman that you are looking for",
                    required = true,
                    example = "1"
            ) @PathParam("id") int id,
            @QueryParam("token") String token
    ) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.getDeliverymanById(id)).build();
        } catch (SQLException | EmployeeDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path(EMPLOYEES + DELIVERYMAN + DELETE + ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes deliveryman employee by the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted delivery employee by the given ID number"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Could not find delivery employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response deleteDeliverymanById(
            @ApiParam(
                    value = "ID of employee that will be deleted",
                    example = "1",
                    required = true
            ) @PathParam("id") int id,
            @QueryParam("token") String token
    ) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            employeeService.deleteDeliverymanById(id);
            return Response.ok().build();
        } catch (DeliverymanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (FailedToDeleteDeliverymanException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path(EMPLOYEES + DELIVERYMAN + ID + UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates deliveryman with the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated delivery employee with the given ID number"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Could not found delivery employee with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response updateDeliverymanById(
            @ApiParam(
                    value = "ID of employee that will be updated",
                    example = "1",
                    required = true
            ) @PathParam("id") int id,
            @ApiParam(
                    value = "Deliveryman data",
                    type = MediaType.APPLICATION_JSON,
                    required = true
            ) DeliveryEmployee deliveryEmployee,
            @QueryParam("token") String token
    ) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            employeeService.updateDeliverymanById(id, deliveryEmployee);
            return Response.ok().build();
        } catch (DeliverymanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FailedToUpdateDeliverymanException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path(EMPLOYEES + SALESMAN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retrieve list of salesman employees",
            tags = HR_TEAM_TAG,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all sales employees from the database",
                    response = SalesEmployee.class),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Could not retrieve sales employees from the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve all sales employees from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response getAllSalesEmployees(@QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.getAllSalesEmployees()).build();
        } catch (FailedToGetAllSalesmanEmployeesException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path(EMPLOYEES + SALESMAN + CREATE)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Creates new sales employee",
            tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added new sales employee to the database"),
            @ApiResponse(code = 400, message = "Failed to add new sales employee to the database"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response createNewSalesman(
            @ApiParam(
                    value = "Salesman data required for creating new salesman",
                    type = MediaType.APPLICATION_JSON,
                    required = true
            ) SalesEmployee salesEmployee,
            @QueryParam("token") String token
    ) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.createNewSalesEmployee(salesEmployee)).build();
        } catch (FailedToCreateNewSalesEmployeeException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path(EMPLOYEES + SALESMAN + ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retrieve salesman employee by the given ID number",
            tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sales employee by the given ID number",
                    response = SalesEmployee.class),
            @ApiResponse(code = 404, message = "Failed to retrieve sales employee by the given ID number"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Could not find sales employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response salesmanById(
            @ApiParam(
                    value = "ID of salesman that you are looking for",
                    example = "1",
                    required = true
            ) @PathParam("id") int id,
            @QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.getSalesmanById(id)).build();
        } catch (EmployeeDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Path(EMPLOYEES + SALESMAN + DELETE + ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes salesman employee by the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted salesman employee by the given ID number"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Could not find salesman employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response deleteSalesmanById(
            @ApiParam(
                    value = "ID of salesman that will be deleted",
                    example = "1",
                    required = true
            ) @PathParam("id") int id,
            @QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            employeeService.deleteSalesmanById(id);
            return Response.ok().build();
        } catch (SalesmanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (FailedToDeleteSalesmanException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @PATCH
    @Path(EMPLOYEES + SALESMAN + ID + UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates deliveryman with the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated delivery employee with the given ID number"),
            @ApiResponse(code = 403, message = "User has no authorities to access resources"),
            @ApiResponse(code = 404, message = "Could not find delivery employee with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response updateSalesmanById(
            @ApiParam(
                    value = "ID of salesman that will be updated",
                    example = "1",
                    required = true
            ) @PathParam("id") int id,
            @ApiParam(
                    value = "Salesman data to be updated",
                    type = MediaType.APPLICATION_JSON,
                    required = true
            ) SalesEmployee salesEmployee,
            @QueryParam("token") String token
    ) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            employeeService.updateSalesmanById(id, salesEmployee);
            return Response.ok().build();
        } catch (SalesmanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (FailedToUpdateSalesmanException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
}


