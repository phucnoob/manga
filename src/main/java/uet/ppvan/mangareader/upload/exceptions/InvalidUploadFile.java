package uet.ppvan.mangareader.upload.exceptions;

import org.springframework.http.HttpStatus;
import uet.ppvan.mangareader.comons.exceptions.BaseException;

public class InvalidUploadFile extends BaseException {

    public InvalidUploadFile(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.EXPECTATION_FAILED;
    }
}
