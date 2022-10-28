package uet.ppvan.mangareader.comons;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
    HttpStatus status,
    Integer code,
    String message,
    LocalDateTime timestamp,
    String path
) {}
