package org.kainos.ea.client;

public class FailedToCreateNewSalesEmployeeException extends Throwable{

    @Override
    public String getMessage() {
        return "Failed to create new salesman in the database";
    }
}
