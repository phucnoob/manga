package uet.ppvan.mangareader.users.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException {

    public UserAlreadyExistException(String msg) {
        super(msg);
    }

    public static UserAlreadyExistException usernameTaken() {
        return new UserAlreadyExistException("Username is taken. Try different name.");
    }

    public static UserAlreadyExistException emailTaken() {
        return new UserAlreadyExistException("Email is taken.");
    }
}
