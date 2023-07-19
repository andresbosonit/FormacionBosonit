package com.example.block7crudvalidation.exceptions;

public class CustomErrorResponse {
    private CustomError error;

    public CustomErrorResponse(CustomError error) {
        this.error = error;
    }

    public CustomError getError() {
        return error;
    }

    public void setError(CustomError error) {
        this.error = error;
    }
}