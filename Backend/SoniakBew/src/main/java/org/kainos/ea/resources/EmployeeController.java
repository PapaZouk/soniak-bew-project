package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.EmployeeService;
import org.kainos.ea.cli.DeliveryEmployee;
import org.kainos.ea.cli.SalesEmployee;
import org.kainos.ea.client.*;
import org.kainos.ea.client.FailedToCreateNewSalesEmployeeException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api
@Path("/")
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
    private final AuthService authService = new AuthService();

    @GET
    @Path(EMPLOYEES + DELIVERYMAN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve list of deliveryman employees", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all delivery employees from the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve all delivery employees from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response getAllDeliveryEmployees() {
        try {
            return Response.ok(employeeService.getAllDeliveryEmployees()).build();
        } catch (FailedToGetAllDeliverymanEmployeesException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path(EMPLOYEES + DELIVERYMAN + CREATE)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Creates new delivery employee", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added new delivery employee to the database"),
            @ApiResponse(code = 400, message = "Failed to add new delivery employee to the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response createNewDeliveryman(DeliveryEmployee deliveryEmployee) {
        try {
            return Response.ok(employeeService.createNewDeliverymanEmployee(deliveryEmployee)).build();
        } catch (FailedToCreateNewDeliverymanEmployeeException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(EMPLOYEES + DELIVERYMAN +ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve deliveryman employee by the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved delivery employee by the given ID number"),
            @ApiResponse(code = 404, message = "Failed to retrieve delivery employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response deliverymanById(@PathParam("id") int id) {
        try {
            return Response.ok(employeeService.getDeliverymanById(id)).build();
        } catch (SQLException | EmployeeDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path(EMPLOYEES + DELIVERYMAN + DELETE + ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes deliveryman employee by the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted delivery employee by the given ID number"),
            @ApiResponse(code = 404, message = "Failed to delete delivery employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response deleteDeliverymanById(@PathParam("id") int id) {
        try {
            employeeService.deleteDeliverymanById(id);
            return Response.ok().build();
        } catch (DeliverymanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FailedToDeleteDeliverymanException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @PATCH
    @Path(EMPLOYEES + DELIVERYMAN + ID + UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates deliveryman with the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated delivery employee with the given ID number"),
            @ApiResponse(code = 404, message = "Failed to update delivery employee with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response updateDeliverymanById(@PathParam("id") int id, DeliveryEmployee deliveryEmployee) {
        try {
            employeeService.updateDeliverymanById(id, deliveryEmployee);
            return Response.ok().build();
        } catch (DeliverymanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FailedToUpdateDeliverymanException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Path(EMPLOYEES + SALESMAN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve list of salesman employees", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all sales employees from the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve all sales employees from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response getAllSalesEmployees() {
        try {
            return Response.ok(employeeService.getAllSalesEmployees()).build();
        } catch (FailedToGetAllSalesmanEmployeesException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path(EMPLOYEES + SALESMAN + CREATE)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Creates new sales employee", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added new sales employee to the database"),
            @ApiResponse(code = 400, message = "Failed to add new sales employee to the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response createNewSalesman(SalesEmployee salesEmployee) {
        try {
            return Response.ok(employeeService.createNewSalesEmployee(salesEmployee)).build();
        } catch (FailedToCreateNewSalesEmployeeException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path(EMPLOYEES + SALESMAN +ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve salesman employee by the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sales employee by the given ID number"),
            @ApiResponse(code = 404, message = "Failed to retrieve sales employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response salesmanById(@PathParam("id") int id) {
        try {
            return Response.ok(employeeService.getSalesmanById(id)).build();
        } catch (EmployeeDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path(EMPLOYEES + SALESMAN + DELETE + ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes salesman employee by the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted salesman employee by the given ID number"),
            @ApiResponse(code = 404, message = "Failed to delete salesman employee by the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response deleteSalesmanById(@PathParam("id") int id) {
        try {
            employeeService.deleteSalesmanById(id);
            return Response.ok().build();
        } catch (SalesmanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FailedToDeleteSalesmanException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }


    @PATCH
    @Path(EMPLOYEES + SALESMAN + ID + UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates deliveryman with the given ID number", tags = HR_TEAM_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated delivery employee with the given ID number"),
            @ApiResponse(code = 404, message = "Failed to update delivery employee with the given ID number"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")
    })
    public Response updateSalesmanById(@PathParam("id") int id, SalesEmployee salesEmployee) {
        try {
            employeeService.updateSalesmanById(id, salesEmployee);
            return Response.ok().build();
        } catch (SalesmanDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FailedToUpdateSalesmanException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }
}


