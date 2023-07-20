package org.kainos.ea.client;

public class FailedToDeleteDeliverymanException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to delete deliveryman";
    }
}
