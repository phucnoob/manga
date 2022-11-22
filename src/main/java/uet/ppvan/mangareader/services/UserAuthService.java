package uet.ppvan.mangareader.services;

import org.springframework.security.core.AuthenticationException;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.exceptions.VerifyEmailFailed;

/**
 * User authentication service.
 */
public interface UserAuthService {
    /**
     * Check if user provided credentials is correct.
     * @param request Login info
     * @return JWT String if login succeed
     * @throws AuthenticationException if credentials is not valid.
     */
    String validateUserLogin(AuthRequest request) throws AuthenticationException;

    /**
     * Send email to user after register.
     * @param email Valid user email
     */
    void sendVerificationEmail(String email);

    /**
     * Validate the token link which user receive in the mail.
     * @see #sendVerificationEmail(String) 
     * @param token generated JWT 
     * @throws VerifyEmailFailed if the request is not from user email (Malformed JWT)
     */

    void verifyEmail(String token) throws VerifyEmailFailed;

    /**
     * Send the password reset link.
     * The link will be expired in 24 hours
     * @param email Valid user email
     */
    void sendPasswordResetEmail(String email);

    /**
     * Handle user request to set new password 
     * @param token JWT token
     * @param password newPassword
     * @see #sendPasswordResetEmail(String) 
     */
    void resetPassword(String token, String password);
}
