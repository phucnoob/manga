package uet.ppvan.mangareader.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailNotVerified extends AuthenticationException {
    public EmailNotVerified(String msg) {
        super(msg);
    }
}
