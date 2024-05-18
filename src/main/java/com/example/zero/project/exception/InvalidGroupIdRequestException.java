package com.example.zero.project.exception;

public class InvalidGroupIdRequestException extends RuntimeException {
    public InvalidGroupIdRequestException(String message) {
        super(message);
    }
}
