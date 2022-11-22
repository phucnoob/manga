package uet.ppvan.mangareader.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uet.ppvan.mangareader.dtos.SuccessResponse;

/**
 * Factory to quickly create ResponseEntity in controller.
 */
public class ResponseFactory {
    public static ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok(
            new SuccessResponse(true, "", data)
        );
    }

    public static ResponseEntity<?> success(String message, Object data) {
        return ResponseEntity.ok(
            new SuccessResponse(true, message, data)
        );
    }

    public static ResponseEntity<?> failure(String message) {
        return failure(message, "");
    }

    public static ResponseEntity<?> failure(String message, Object data) {
        return failure(message, data, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> failure(String message, Object data, HttpStatus status) {
        return ResponseEntity.status(status)
            .body(
                new SuccessResponse(false, message, data)
            );
    }
}
