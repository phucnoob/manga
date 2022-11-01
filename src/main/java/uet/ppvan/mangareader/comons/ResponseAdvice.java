package uet.ppvan.mangareader.comons;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uet.ppvan.mangareader.comons.exceptions.BaseException;
import uet.ppvan.mangareader.comons.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.upload.exceptions.InvalidUploadFile;


public class ResponseAdvice extends ResponseEntityExceptionHandler {

//    Global -fallback
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseFactory.failure(ex.getMessage()));
    }


    @ExceptionHandler(NoSuchElementFound.class)
    public ResponseEntity<Object> handleNotFoundException(
        BaseException exception,
        HttpServletRequest httpRequest
    ) {
        logger.info(exception.getMessage());
        return buildResponse(exception, httpRequest);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeExceed(
        MaxUploadSizeExceededException ex,
        HttpServletRequest request
    ) {
        String message = "Filesize can't exceed 5MB";
        logger.info(ex.getMessage());

        return buildResponse(new InvalidUploadFile(message), request);
    }
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleCustomAPIException(
        BaseException ex,
        HttpServletRequest request
    ) {
        logger.info(ex.getMessage());
        String message = ex.getMessage();
        var response = new ErrorResponse(
            ex.getStatus(),
            ex.getStatus().value(),
            message,
            LocalDateTime.now(ZoneOffset.UTC),
            request.getRequestURI()
        );

        return new ResponseEntity<>(response, ex.getStatus());
    }

    private ResponseEntity<Object> buildResponse(BaseException ex, HttpServletRequest request) {

        var response = new ErrorResponse(
            ex.getStatus(),
            ex.getStatus().value(),
            ex.getMessage(),
            LocalDateTime.now(ZoneOffset.UTC),
            request.getRequestURI()
        );

        return new ResponseEntity<>(response, response.status());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleDataValidation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseFactory.failure(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseFactory.failure(ex.getMessage()));
    }


}
