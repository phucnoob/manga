package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchElementFound extends BaseException {
    public NoSuchElementFound(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
