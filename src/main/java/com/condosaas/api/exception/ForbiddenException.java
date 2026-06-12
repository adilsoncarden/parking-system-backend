package com.condosaas.api.exception;

// Se lanza cuando un usuario intenta acceder a datos de un condominio que no le corresponde.
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
