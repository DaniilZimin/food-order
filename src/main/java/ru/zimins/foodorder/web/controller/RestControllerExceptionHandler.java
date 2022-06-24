package ru.zimins.foodorder.web.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;
import ru.zimins.foodorder.exception.ValidationException;
import ru.zimins.foodorder.web.ExceptionResponse;

import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({ValidationException.class, DataIntegrityViolationException.class, NotFoundException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidFieldException(RuntimeException exception) {
        exception.printStackTrace();
        ExceptionResponse build = ExceptionResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(build);
    }
}
