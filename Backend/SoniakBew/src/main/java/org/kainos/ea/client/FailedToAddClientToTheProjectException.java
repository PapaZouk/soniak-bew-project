package org.kainos.ea.client;

public class FailedToAddClientToTheProjectException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to add client to the project";
    }
}
