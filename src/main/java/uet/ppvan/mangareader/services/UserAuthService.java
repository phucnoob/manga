package uet.ppvan.mangareader.services;

import org.springframework.security.core.AuthenticationException;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.exceptions.VerifyEmailFailed;

public interface UserAuthService {
    String validateUserLogin(AuthRequest request) throws AuthenticationException;

    void sendVerificationEmail(String email);

    void verifyEmail(String token) throws VerifyEmailFailed;
}
