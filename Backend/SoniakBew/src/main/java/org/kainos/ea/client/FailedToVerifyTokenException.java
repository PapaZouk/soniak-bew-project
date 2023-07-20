package org.kainos.ea.client;

public class FailedToVerifyTokenException extends Throwable {
    public String getMessage() {
        return "Failed to verify token";
    }
}
