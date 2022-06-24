package ru.zimins.foodorder.web;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ExceptionResponse {

    private OffsetDateTime timestamp;
    private Integer code;
    private String message;
}
