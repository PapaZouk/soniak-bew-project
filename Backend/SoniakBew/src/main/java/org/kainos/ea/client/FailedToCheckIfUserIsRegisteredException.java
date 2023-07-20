package org.kainos.ea.client;

public class FailedToCheckIfUserIsRegisteredException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to check if user is registered";
    }
}
