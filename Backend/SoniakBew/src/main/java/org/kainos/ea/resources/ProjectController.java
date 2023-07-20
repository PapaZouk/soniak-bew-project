package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.kainos.ea.api.ProjectService;
import org.kainos.ea.cli.Project;
import org.kainos.ea.cli.ProjectRequest;
import org.kainos.ea.client.FailedToCreateNewProjectException;
import org.kainos.ea.client.FailedToUpdateProjectStatusException;
import org.kainos.ea.client.ProjectDoesNotExistException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api
@Path("/")
public class ProjectController {
    private final ProjectService projectService = new ProjectService();
    private static final String MANAGEMENT_TAG = "Management Team";
    private static final String PROJECTS = "projects";
    private static final String CREATE = "/create";
    private static final String UPDATE_STATUS = "/updatestatus";
    private static final String PROJECT_ID = "/{id}";

    @GET
    @Path(PROJECTS)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve list of deliveryman employees", tags = MANAGEMENT_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve all projects from " +
                    "the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve all projects from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")})

    public List<Project> getAllProject() {
        return projectService.getAllProject();

    }

    @GET
    @Path(PROJECTS + PROJECT_ID)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve project by the given ID number", tags = MANAGEMENT_TAG)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve project by ID from " +
                    "the database"),
            @ApiResponse(code = 404, message = "Failed to retrieve project from the database"),
            @ApiResponse(code = 500, message = "Failed to connect with the database")})
    public Response getProjectById(@PathParam("id") int id) {
        try {
            return Response.ok(projectService.getProjectById(id)).build();
        } catch (ProjectDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path(PROJECTS + PROJECT_ID + UPDATE_STATUS)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates project status by the given ID number", tags = MANAGEMENT_TAG)
    public Response updateProjectStatusAsCompleted(@PathParam("id") int id) {
        try {
            projectService.setProjectStatusAsCompletedByProjectId(id);
            return Response.ok().build();
        } catch (ProjectDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (FailedToUpdateProjectStatusException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @POST
    @Path(PROJECTS + CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new project", tags = MANAGEMENT_TAG)
    public Response createNewProject(ProjectRequest projectRequest) {
        try {
            projectService.createNewProject(projectRequest);
            return Response.ok().build();
        } catch (FailedToCreateNewProjectException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

}