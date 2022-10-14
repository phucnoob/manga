package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUploadFile extends BaseException {

    public InvalidUploadFile(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.EXPECTATION_FAILED;
    }
}
