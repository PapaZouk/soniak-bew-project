package org.kainos.ea.client;

public class FailedToGetAllDeliverymanEmployeesException extends Throwable{

    @Override
    public String getMessage() {
        return "Failed to get list of deliveryman employees";
    }
}
