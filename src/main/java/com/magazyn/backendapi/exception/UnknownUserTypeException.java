package com.magazyn.backendapi.exception;

public class UnknownUserTypeException extends RuntimeException{
    public UnknownUserTypeException(String message) {
        super(message);
    }
}
