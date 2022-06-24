package ru.zimins.foodorder.web.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.zimins.foodorder.exception.ValidationException;
import ru.zimins.foodorder.web.ExceptionResponse;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidFieldException(ValidationException validationException) {
        validationException.printStackTrace();
        ExceptionResponse build = ExceptionResponse.builder()
                .message(validationException.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(build);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidFieldException(DataIntegrityViolationException dataIntegrityViolationException) {
        dataIntegrityViolationException.printStackTrace();
        ExceptionResponse build = ExceptionResponse.builder()
                .message(dataIntegrityViolationException.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(build);
    }
}
