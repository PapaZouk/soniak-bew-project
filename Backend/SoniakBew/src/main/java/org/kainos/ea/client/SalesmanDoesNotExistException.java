package org.kainos.ea.client;

public class SalesmanDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to retrieve salesman from the database";
    }
}
