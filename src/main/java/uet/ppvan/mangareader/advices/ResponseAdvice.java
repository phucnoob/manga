package uet.ppvan.mangareader.advices;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uet.ppvan.mangareader.dtos.ErrorResponse;
import uet.ppvan.mangareader.exceptions.ResourceNotFound;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
@SuppressWarnings("unused")
public class ResponseAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFound.class)
    public final ResponseEntity<?> handleResouceNotfound(ResourceNotFound ex, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        var error = new ErrorResponse("Record not found", details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(error);
    }

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
}
