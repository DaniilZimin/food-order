package ru.zimins.foodorder.web;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ExceptionResponse {

    OffsetDateTime timestamp;
    Integer code;
    String message;
}
