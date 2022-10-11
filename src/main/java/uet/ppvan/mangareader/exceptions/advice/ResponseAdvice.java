package uet.ppvan.mangareader.exceptions.advice;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNullApi;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.exceptions.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoSuchElementFound.class})
    public ResponseEntity<Object> handleException(
        ServiceException exception,
        WebRequest request,
        HttpServletRequest httpRequest
    ) {
        System.out.println("Hi");
        var response = new ExceptionResponse(
            exception.getStatus(),
            exception.getStatus().value(),
            exception.getMessage(),
            httpRequest.getRequestURI()
        );
        return new ResponseEntity<>(response, new HttpHeaders(), response.status());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        String error = "JSON invalid";
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseFromException(ExceptionResponse response) {
        return new ResponseEntity<>(response, response.status());
    }
}
