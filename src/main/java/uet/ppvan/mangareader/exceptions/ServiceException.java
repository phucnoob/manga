package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();
}
