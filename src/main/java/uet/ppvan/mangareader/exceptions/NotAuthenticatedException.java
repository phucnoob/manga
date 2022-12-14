package uet.ppvan.mangareader.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if user access resource that need authentication.
 */
public class NotAuthenticatedException extends AuthenticationException {
    public NotAuthenticatedException(String msg) {
        super(msg);
    }

    public NotAuthenticatedException() {
        this("You're not login. Please goto login page.");
    }
}
