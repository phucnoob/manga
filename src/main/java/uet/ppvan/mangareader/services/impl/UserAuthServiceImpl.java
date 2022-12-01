package uet.ppvan.mangareader.services.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.exceptions.EmailNotVerified;
import uet.ppvan.mangareader.exceptions.PasswordNotMatchException;
import uet.ppvan.mangareader.exceptions.VerifyEmailFailed;
import uet.ppvan.mangareader.repositories.UserRepository;
import uet.ppvan.mangareader.services.JWTService;
import uet.ppvan.mangareader.services.UserAuthService;

import java.time.Duration;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private final JWTService defaultJWTService;

    @Value("${config.host}")
    private String domainName;

    @Override
    public String validateUserLogin(AuthRequest request) {

        var user = repository.findUserByUsername(request.username())
                       .orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exists", request.username())));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw PasswordNotMatchException.defaultValue();
        }

        if (!user.getVerified()) {
            throw new EmailNotVerified("Your email hasn't been verified yet. Check your mailbox");
        }

        AuthUserDetail authUser = new AuthUserDetail(user.getId(), user.getUsername(), user.getRole().getRole());

        return defaultJWTService.generateJWT(authUser, Duration.ofDays(30));
    }

    @Override
    @Async
    public void sendVerificationEmail(String email) {
        var message = new SimpleMailMessage();
        message.setSubject("Verification email");
        String token = defaultJWTService.generateJWT(email, Duration.ofHours(24));

        // FIXME: 11/19/22 Hard-code but i don't know how to fix.
        message.setText(String.format("%s/api/v1/users/verify?token=%s", domainName, token));
        message.setTo(email);

        mailSender.send(message);
    }

    @Override
    public void verifyEmail(String token) throws VerifyEmailFailed {
        try {
            String email = defaultJWTService.parseJWT(String.class, token);
            var user = repository.findUserByEmail(email)
                           .orElseThrow(() -> new VerifyEmailFailed("Email is not verified."));

            user.setVerified(true);
            repository.save(user);
            log.debug("{} is verified", email);
        } catch (ExpiredJwtException ex) {
            throw new JwtException("JWT is expired, Please request a new one.", ex);
        } catch (MalformedJwtException ex) {
            throw new JwtException("JWT is not valid, Please ensure you get it from out server");
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String email) {
        var message = new SimpleMailMessage();
        message.setSubject("Password Reset email");
        String token = defaultJWTService.generateJWT(email, Duration.ofHours(24));

        // FIXME: 11/19/22 Hard-code but i don't know how to fix.
        message.setText(String.format("%s/api/v1/users/password-reset?email=%s", domainName, token));
        message.setTo(email);

        mailSender.send(message);
    }

    @Override
    public void resetPassword(String token, String password) {
        try {
            String email = defaultJWTService.parseJWT(String.class, token);
            repository.findUserByEmail(email)
            .ifPresent(user -> {
                user.setPassword(password);
                repository.save(user);
            });
        } catch (ExpiredJwtException ex) {
            throw new JwtException("JWT is expired, Please request a new one.", ex);
        } catch (MalformedJwtException ex) {
            throw new JwtException("JWT is not valid, Please ensure you get it from out server");
        }
    }

}
