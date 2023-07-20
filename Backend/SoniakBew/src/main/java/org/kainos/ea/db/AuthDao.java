package org.kainos.ea.db;

import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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

}
