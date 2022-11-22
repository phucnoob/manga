package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown if file upload is interrupted (by network, hardware...)
 */
public class UploadFileInterrupt extends BaseException {

    private UploadFileInterrupt(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public static UploadFileInterrupt withMessage(String message) {
        return new UploadFileInterrupt(message);
    }
}
