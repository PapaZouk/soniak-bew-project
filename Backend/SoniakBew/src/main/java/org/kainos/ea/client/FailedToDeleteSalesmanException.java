package org.kainos.ea.client;

public class FailedToDeleteSalesmanException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to delete salesman employee from the database";
    }
}
