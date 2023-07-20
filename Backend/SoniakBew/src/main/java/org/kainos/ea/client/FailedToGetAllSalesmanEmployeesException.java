package org.kainos.ea.client;

public class FailedToGetAllSalesmanEmployeesException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get list of salesman employees";
    }
}
