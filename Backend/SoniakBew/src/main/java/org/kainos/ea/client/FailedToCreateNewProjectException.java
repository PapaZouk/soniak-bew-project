package org.kainos.ea.client;

public class FailedToCreateNewProjectException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to create new project";
    }
}
