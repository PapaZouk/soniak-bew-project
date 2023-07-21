package org.kainos.ea.client;

public class FailedToGetProjectsException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get projects";
    }
}
