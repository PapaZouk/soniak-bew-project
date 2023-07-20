package org.kainos.ea.api;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.kainos.ea.cli.Project;
import org.kainos.ea.cli.ProjectRequest;
import org.kainos.ea.client.FailedToCreateNewProjectException;
import org.kainos.ea.client.FailedToUpdateProjectStatusException;
import org.kainos.ea.client.ProjectDoesNotExistException;
import org.kainos.ea.db.ProjectDao;

public class ProjectService {

    private final ProjectDao projectDao = new ProjectDao();

    public List<Project> getAllProject(){

        List<Project> projectList =  projectDao.getAllProject();
        return projectList;
    }

    public Project getProjectById(int id) throws ProjectDoesNotExistException {
        try {
            return projectDao.getProjectById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ProjectDoesNotExistException();
        }
    }

    public void setProjectStatusAsCompletedByProjectId(int id)
            throws ProjectDoesNotExistException, FailedToUpdateProjectStatusException {
        try {
            Project projectToUpdate = getProjectById(id);

            if (Objects.isNull(projectToUpdate)) {
                throw new ProjectDoesNotExistException();
            }

            projectDao.setProjectStatusAsCompletedByProjectId(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateProjectStatusException();
        }

    }

    public void createNewProject(ProjectRequest projectRequest)
            throws FailedToCreateNewProjectException {
        try {
            int result = projectDao.addNewProject(projectRequest);

            if (result == -1) {
                throw new FailedToCreateNewProjectException();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateNewProjectException();
        }
    }
}
