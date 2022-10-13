package uet.ppvan.mangareader.exceptions.advice;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import uet.ppvan.mangareader.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.exceptions.BaseException;

@RestControllerAdvice
public class ResponseAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementFound.class)
    public ResponseEntity<Object> handleNotFoundException(
        BaseException exception,
        HttpServletRequest httpRequest
    ) {
        logger.info(exception.getMessage());
        var response = new ErrorResponse(
            exception.getStatus(),
            exception.getStatus().value(),
            exception.getMessage(),
            LocalDateTime.now(ZoneOffset.UTC),
            httpRequest.getRequestURI()
        );
        return buildResponseFromException(response);
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<Object> handleCustomAPIException(BaseException ex,
        HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        logger.info(ex.getMessage());
        String message = ex.getMessage();
        var response = new ErrorResponse(
            ex.getStatus(),
            ex.getStatus().value(),
            message,
            LocalDateTime.now(ZoneOffset.UTC),
            request.getRequestURI()
        );

        return new ResponseEntity<>(response, headers, status);
    }

    private ResponseEntity<Object> buildResponseFromException(ErrorResponse response) {
        return new ResponseEntity<>(response, response.status());
    }
}
