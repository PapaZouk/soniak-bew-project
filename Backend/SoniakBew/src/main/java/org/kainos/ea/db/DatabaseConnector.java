package org.kainos.ea.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        String user, password, host, name;

        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try (FileInputStream propsStream = new FileInputStream("db.properties")) {

            Properties props = new Properties();
            props.load(propsStream);
            user = props.getProperty("DB_USERNAME");
            password = props.getProperty("DB_PASSWORD");
            host = props.getProperty("DB_HOST");
            name = props.getProperty("DB_NAME");

            if (user == null || password == null || host == null) {
                throw new IllegalAccessException(
                        "Properties file must exist and must contain user, password, name and host properties"
                );
            }

            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + "/" + name,
                    user,
                    password
            );
            return connection;
        } catch (Exception e) {
            System.err.println("Exception while connecting to the database. " + e.getMessage());
        }
        return null;
    }
}
