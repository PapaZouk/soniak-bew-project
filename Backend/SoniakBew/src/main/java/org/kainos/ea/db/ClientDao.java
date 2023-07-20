package org.kainos.ea.db;

import org.kainos.ea.cli.Client;
import org.kainos.ea.cli.RequestClientWithMaxValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<Client> getAllClients() {
        try (Connection c = databaseConnector.getConnection()) {

//        Connection c =
//                databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT id, phone_number, first_name, last_name, address " +
                "FROM client");

        List<Client> clientList = new ArrayList<>();

        while (rs.next()) {
            Client client = new Client(
                    rs.getInt("id"),
                    rs.getString("phone_number"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address")
            );

            clientList.add(client);
        }

            return clientList;
         }catch (SQLException e){

            System.err.println(e.getMessage());
        }
        return null;
    }


    public RequestClientWithMaxValue getClientWithMaxValue() {
        try (Connection c = databaseConnector.getConnection()) {

            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT concat(last_name, ' ', first_name) AS " +
                    "ClientName, value AS total_project_value " +
                    "FROM client " +
                    "JOIN project " +
                    "GROUP BY client_id " +
                    "ORDER BY total_project_value DESC " +
                    "LIMIT 1;");

            while (rs.next()) {
                return new RequestClientWithMaxValue(
                        rs.getString("ClientName"),
                        rs.getString("total_project_value")
                );
            }

            return null;
        }catch (SQLException e){

            System.err.println(e.getMessage());
        }
        return null;
    }


}
