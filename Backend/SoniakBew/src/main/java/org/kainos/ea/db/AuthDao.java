package org.kainos.ea.db;

import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Login;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class AuthDao extends DatabaseConnector {

    public String generateToken(String username) throws SQLException {
        String token = UUID.randomUUID().toString();

        Date expiry = DateUtils.addHours(new Date(), 8);

        Connection conn = getConnection();

        String query = "INSERT INTO Token (Username, Token, Expiry) " +
                "VALUES (?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, token);
        statement.setTimestamp(3, new Timestamp(expiry.getTime()));

        statement.executeUpdate();

        return token;
    }

    public boolean validLogin(Login login) throws SQLException {
        Connection conn = getConnection();

        String query = "SELECT us.password FROM user AS us WHERE us.username = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, login.getUsername());

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            return result.getString("password").equals(login.getPassword());
        }
        return false;
    }

    public boolean isUserRegistered(String username) throws SQLException {
        Connection conn = getConnection();

        String query = "SELECT username FROM user WHERE username = ?";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, username);
        statement.executeQuery();

        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            return true;
        }
        return false;
    }

    public int registerUser(Login login) throws SQLException {
        Connection conn = getConnection();

        String insertStatement = "INSERT INTO user (username, password, roleId) " +
                "VALUES (?, ?, 2)";

        PreparedStatement statement = conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, login.getUsername());
        statement.setString(2, login.getPassword());

        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public int getRoleIDFromToken(String token) throws SQLException, TokenExpiredException {
        Connection conn = getConnection();

        String query = "SELECT roleId, expiry FROM user " +
                "JOIN token USING (username) " +
                "WHERE token = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, token);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Timestamp expiry = result.getTimestamp("expiry");

            if (expiry.after(new Date())) {
                return result.getInt("roleId");
            } else {
                throw new BatchUpdateException();
            }
        }
        return -1;
    }
}
