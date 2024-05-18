package com.example.zero.base;

import com.example.zero.user.exception.InvalidRegisterException;
import com.example.zero.user.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRegisterException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidReservationException(InvalidRegisterException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> handleLoginException(LoginException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> validException(MethodArgumentNotValidException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("Validation Failed : " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpStatusCodeException.class})
    public ResponseEntity<ExceptionResponse> validException(HttpStatusCodeException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, ex.getStatusCode());
    }
}
