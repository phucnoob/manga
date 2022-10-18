package uet.ppvan.mangareader.upload.exceptions;

import org.springframework.http.HttpStatus;
import uet.ppvan.mangareader.comons.exceptions.BaseException;

public class UploadFileInterupt extends BaseException {

    private UploadFileInterupt(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public static UploadFileInterupt withMessage(String message) {
        return new UploadFileInterupt(message);
    }
}
