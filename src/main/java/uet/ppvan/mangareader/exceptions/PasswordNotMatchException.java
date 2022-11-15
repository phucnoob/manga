package uet.ppvan.mangareader.exceptions;


import org.springframework.security.core.AuthenticationException;

public class PasswordNotMatchException extends AuthenticationException {
    public PasswordNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PasswordNotMatchException(String msg) {
        super(msg);
    }

    public static PasswordNotMatchException defaultValue() {
        return new PasswordNotMatchException("Password is not matched.");
    }
}
