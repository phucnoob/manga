package uet.ppvan.mangareader.comons.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends BaseException {
    public ResourceNotFound(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
