package org.kainos.ea.api;


import org.kainos.ea.cli.Login;
import org.kainos.ea.cli.LoginNoRole;
import org.kainos.ea.client.*;
import org.kainos.ea.db.AuthDao;

import java.sql.SQLException;

public class AuthService {
    private AuthDao authDao = new AuthDao();

    public String login(LoginNoRole login) throws FailedToLoginException, FailedToGenerateTokenException {

        try {
            if (authDao.validLogin(login)) {
                return authDao.generateToken(login.getUsername());
            }
        } catch (SQLException e) {
            throw new FailedToGenerateTokenException();
        }

        throw new FailedToLoginException();
    }

    public boolean isAdmin(String token) throws TokenExpiredException, FailedToVerifyTokenException {
        try {
            int roleID = authDao.getRoleIDFromToken(token);
            if (roleID == 1) {

                return true;
            }
        } catch (SQLException e) {
            throw new FailedToVerifyTokenException();
        }
        return false;
    }

    public boolean isManager(String token) throws TokenExpiredException, FailedToVerifyTokenException {
        try {
            int roleID = authDao.getRoleIDFromToken(token);
            if (roleID == 2) {

                return true;
            }
        } catch (SQLException e) {
            throw new FailedToVerifyTokenException();
        }
        return false;
    }

    public boolean isSales(String token) throws TokenExpiredException, FailedToVerifyTokenException {
        try {
            int roleID = authDao.getRoleIDFromToken(token);
            if (roleID == 3) {

                return true;
            }
        } catch (SQLException e) {
            throw new FailedToVerifyTokenException();
        }
        return false;
    }

    public boolean isHr(String token) throws TokenExpiredException, FailedToVerifyTokenException {
        try {
            int roleID = authDao.getRoleIDFromToken(token);
            if (roleID == 4) {

                return true;
            }
        } catch (SQLException e) {
            throw new FailedToVerifyTokenException();
        }
        return false;
    }

    public boolean isUserRegistered(String username) throws FailedToCheckIfUserIsRegisteredException {
        try {
            return authDao.isUserRegistered(username);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCheckIfUserIsRegisteredException();
        }
    }

    public void registerUser(Login login)
            throws FailedToRegisterNewUserException, FailedToCheckIfUserIsRegisteredException
    {
        try {
            if (isUserRegistered(login.getUsername())) {
                throw new FailedToRegisterNewUserException();
            }
            authDao.registerUser(login);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToRegisterNewUserException();
        }
    }

}
