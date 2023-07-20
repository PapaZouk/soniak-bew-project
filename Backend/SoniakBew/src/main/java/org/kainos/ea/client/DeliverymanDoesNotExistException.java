package org.kainos.ea.client;

public class DeliverymanDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to retrieve deliveryman from the database";
    }
}
