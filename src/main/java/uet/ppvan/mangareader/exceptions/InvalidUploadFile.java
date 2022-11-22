package uet.ppvan.mangareader.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown if the uploaded file does not follow rules.<br>
 * - Filesize < 5MB<br>
 * - Filename is not empty<br>
 * - Must be an image (might change in future)
 */
public class InvalidUploadFile extends BaseException {

    public InvalidUploadFile(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.EXPECTATION_FAILED;
    }
}
