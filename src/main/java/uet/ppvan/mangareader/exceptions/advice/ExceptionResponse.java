package uet.ppvan.mangareader.exceptions.advice;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(
    HttpStatus status,
    Integer code,
    String message,
    String path
) {}
