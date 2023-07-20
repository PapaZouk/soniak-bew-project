package org.kainos.ea.client;

public class FailedToRegisterNewUserException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to register new user";
    }
}
