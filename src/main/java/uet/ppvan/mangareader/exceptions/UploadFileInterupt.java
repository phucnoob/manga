package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

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
