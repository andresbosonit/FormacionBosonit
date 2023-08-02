package com.example.jpacascade.exceptions;

import java.util.Date;

public class EntityNotFoundException extends RuntimeException{
    private final CustomError error;

    public EntityNotFoundException(String message) {
        super(message);
        error = new CustomError(new Date(), 404, message);
    }

    public CustomError getError() {
        return error;
    }
}
