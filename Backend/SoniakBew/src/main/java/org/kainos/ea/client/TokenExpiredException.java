package org.kainos.ea.client;

import jdk.javadoc.internal.doclets.toolkit.taglets.ThrowsTaglet;

public class TokenExpiredException extends Throwable {
    @Override
    public String getMessage(){
        return "Token expired";
    }
}
