package uet.ppvan.mangareader.mangas.advice;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uet.ppvan.mangareader.dto.ObjectResponse;
import uet.ppvan.mangareader.dto.ResponseFactory;
import uet.ppvan.mangareader.exceptions.BaseException;
import uet.ppvan.mangareader.exceptions.NoSuchElementFound;
import uet.ppvan.mangareader.mangas.enums.EnumParseException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MangaValidation {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ObjectResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.failure("Manga is invalid.", errors));
    }

    @ExceptionHandler(EnumParseException.class)
    public ResponseEntity<Object> handleParseEnumError(EnumParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.failure(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementFound.class)
    public ResponseEntity<Object> handleNotFoundException(
            BaseException exception,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseFactory.failure(exception.getMessage()));
    }
}
