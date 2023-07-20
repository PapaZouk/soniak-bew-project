package org.kainos.ea.client;

public class FailedToUpdateSalesmanException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to update salesman";
    }
}
