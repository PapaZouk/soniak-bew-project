package org.kainos.ea.client;

public class FailedToUpdateProjectStatusException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to update project status";
    }
}
