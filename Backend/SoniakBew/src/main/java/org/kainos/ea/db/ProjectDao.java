package org.kainos.ea.db;


import org.kainos.ea.cli.Project;
import org.kainos.ea.cli.ProjectRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao extends DatabaseConnector {

    public void addClientToTheProjectWithID(int projectId, int clientId) throws SQLException {
        Connection conn = getConnection();

        String query = "UPDATE project AS p " +
                "   SET p.client_id = ? " +
                "   WHERE p.id = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, clientId);
        statement.setInt(2, projectId);

        statement.executeUpdate();
    }

    public List<Project> getAllProject() throws SQLException {
        Connection c = getConnection();
        Statement st = c.createStatement();

        String query = "SELECT pr.id, pr.tech_lead_id, CONCAT(e.first_name, ' ', e.last_name) AS `employee`, " +
                "pr.client_id, pr.name, pr.value, pr.status, pr.start_date, pr.complete_date " +
                "FROM project AS pr " +
                "     LEFT JOIN employee AS e ON e.id = pr.tech_lead_id;";

        ResultSet rs = st.executeQuery(query);
        List<Project> projectList = new ArrayList<>();

        while (rs.next()) {
            Project project = Project.builder()
                    .id(rs.getInt("id"))
                    .tech_lead_id(rs.getInt("tech_lead_id"))
                    .techLeadName(rs.getString("employee"))
                    .client_id(rs.getInt("client_id"))
                    .name(rs.getString("name"))
                    .value(rs.getFloat("value"))
                    .status(rs.getString("status"))
                    .start_date(rs.getDate("start_date"))
                    .complete_date(rs.getDate("complete_date"))
                    .build();
            projectList.add(project);
        }
        return projectList;
    }

    public Project getProjectById(int id) throws SQLException {
        Connection conn = getConnection();

        String query = "SELECT pr.id, pr.tech_lead_id, pr.client_id, pr.name, " +
                "pr.value, pr.status, pr.start_date, pr.complete_date " +
                "FROM project as pr " +
                "WHERE pr.id = ?;";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            return new Project(
                    result.getInt("id"),
                    result.getInt("tech_lead_id"),
                    result.getString(""),
                    result.getInt("client_id"),
                    result.getString("name"),
                    result.getInt("value"),
                    result.getString("status"),
                    result.getDate("start_date"),
                    result.getDate("complete_date")
            );
        }
        return null;
    }

    public int addNewProject(ProjectRequest projectRequest) throws SQLException {
        Connection conn = getConnection();

        String query = "INSERT INTO project (tech_lead_id, client_id, name, value, status, start_date, complete_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, projectRequest.getTech_lead_id());
        statement.setInt(2, projectRequest.getClient_id());
        statement.setString(3, projectRequest.getName());
        statement.setFloat(4, projectRequest.getValue());
        statement.setString(5, projectRequest.getStatus());
        statement.setDate(6, new Date(projectRequest.getStart_date().getTime()));
        statement.setDate(7, new Date(projectRequest.getComplete_date().getTime()));

        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public void setProjectStatusAsCompletedByProjectId(int id) throws SQLException {
        Connection conn = getConnection();

        String query = "UPDATE project AS pr " +
                "SET pr.complete_date = NOW() " +
                "WHERE pr.id = ?;";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        statement.execute();
    }

    public void setDeliverymanToProjectWithId(int projectId, int deliverymanId) throws SQLException {
        Connection conn = getConnection();

        String query = "";
    }

}
