package com.example.zero.user.exception;

public class ActivationFailedException extends RuntimeException{
    public ActivationFailedException(String message) {
        super(message);
    }
}
