package uet.ppvan.mangareader.exceptions.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
    HttpStatus status,
    Integer code,
    String message,
    LocalDateTime timestamp,
    String path
) {}
