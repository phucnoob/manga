package uet.ppvan.mangareader.advices;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uet.ppvan.mangareader.dtos.ErrorResponse;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;
import uet.ppvan.mangareader.exceptions.UserAlreadyExistException;
import uet.ppvan.mangareader.exceptions.VerifyEmailFailed;
import uet.ppvan.mangareader.utils.ResponseFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Global ResponseAdvice.
 */
@RestControllerAdvice
@SuppressWarnings("unused")
public class ResponseAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFound.class)
    public final ResponseEntity<?> handleResourceNotfound(ResourceNotFound ex, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        var error = new ErrorResponse("Record not found", details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(error);
    }

    /**
     * Default handler if {@link javax.validation.Valid @Valid} annotation failed.
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return {@link ResponseEntity}
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        var response = new ErrorResponse("Data validation failed.", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(response);
    }

    /**
     * Default handler if JSON can't be parsed in
     * {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}.
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return {@link ResponseEntity}
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String message = "Can't not parse requestBody";
        Throwable cause = ex;
        List<String> details = new ArrayList<>();
        while (cause.getCause() != null) {
            details.add(cause.getMessage());
            cause = cause.getCause();
        }
        var response = new ErrorResponse(message, details);

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
        .body(response);
    }

    /**
     * Handler if user had registered but hasn't verified email.
     * @param ex the exception
     * @param request the current request
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(VerifyEmailFailed.class)
    public ResponseEntity<?> handleVerifyEmailFailed(VerifyEmailFailed ex, WebRequest request) {
        return ResponseFactory.failure("Invalid or expired token");
    }

    /**
     * Handler if a JWT-related Exception is thrown.
     * @param ex the exception
     * @param request the current request
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException ex, WebRequest request) {
        return ResponseFactory.failure(ex.getMessage());
    }

    /**
     * Default handler for Spring security {@link AuthenticationException} Exception.
     * @param ex The exception
     * @param request current request
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleVerifyEmailFailed(AuthenticationException ex, WebRequest request) {
        return ResponseFactory.failure(
        "Authentication failed.",
            ex.getLocalizedMessage(),
            HttpStatus.UNAUTHORIZED
        );
    }


    /**
     * Handle if username or email or any user info already existed in database.
     * @param ex The exception
     * @param request current request
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExist(AuthenticationException ex, WebRequest request) {
        return ResponseFactory.failure(
    "User already exists",
            ex.getLocalizedMessage(),
            HttpStatus.EXPECTATION_FAILED
        );
    }
}
