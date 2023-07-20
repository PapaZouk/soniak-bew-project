package org.kainos.ea.client;

public class ProjectDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get project";
    }
}
