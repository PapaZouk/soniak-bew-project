package org.kainos.ea.client;

public class FailedToUpdateDeliverymanException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to update deliveryman";
    }
}
