package uet.ppvan.mangareader.services.impl;

import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uet.ppvan.mangareader.dtos.LoginForm;
import uet.ppvan.mangareader.dtos.PasswordResetForm;
import uet.ppvan.mangareader.dtos.ProfileRequest;
import uet.ppvan.mangareader.dtos.RegisterForm;
import uet.ppvan.mangareader.exceptions.UserAlreadyExistException;
import uet.ppvan.mangareader.mappers.UserMapper;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.UserRepository;
import uet.ppvan.mangareader.services.JWTService;
import uet.ppvan.mangareader.services.ProfileService;

import java.time.Duration;


@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class FormUserService {

    private final UserRepository userRepository;

    private final ProfileService profileService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JavaMailSender mailSender;

    private final JWTService defaultJWTService;

    @Value("${config.host}")
    private String domainName;

    @Async
    public void sendPasswordResetEmail(String email, String handleUrl) {
        var message = new SimpleMailMessage();
        message.setSubject("Password Reset");
        String token = defaultJWTService.generateJWT(email, Duration.ofHours(24));

        message.setText(String.format("%s%s?token=%s", domainName, handleUrl, token));
        message.setTo(email);

        mailSender.send(message);

        log.info("Password reset mail to {} sent.", email);
    }

    public String parseEmailFromPasswordResetToken(String token) {
        return defaultJWTService.parseJWT(String.class, token);
    }

    @Transactional
    public User resetPassword(@Valid PasswordResetForm form, String email) {
        var user = userRepository.findUserByEmail(email)
                       .orElseThrow(() -> new UsernameNotFoundException("Email doesn't exist"));

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));

        return userRepository.save(user);
    }

    @Async
    public void sendVerifiedEmail(@Valid RegisterForm form, String handleUrl) {
        var message = new SimpleMailMessage();
        message.setSubject("Please verify your email");
        String token = defaultJWTService.generateJWT(form.getEmail(), Duration.ofHours(24));

        message.setText(String.format("%s%s?token=%s", domainName, handleUrl, token));
        message.setTo(form.getEmail());

        mailSender.send(message);

        log.info("Verify mail to {} sent.", form.getEmail());
    }

    @Transactional
    public User verifyAccount(String token) throws JwtException {
        String email = defaultJWTService.parseJWT(String.class, token);
        var user = userRepository.findUserByEmail(email)
                       .orElseThrow(() -> new UsernameNotFoundException("Email doesn't exist"));

        user.setVerified(true);

        return userRepository.save(user);
    }

    public boolean validCredentials(@Valid LoginForm form) {

        return userRepository
                   .findUserByUsername(form.getUsername())
                   .map(user -> passwordEncoder.matches(form.getPassword(), user.getPassword()))
                   .orElse(false);
    }

    @Transactional
    public User createUser(@Valid RegisterForm form) {

        log.info("FormUserService#createUser create new user: {}", form.getUsername());

        if (userRepository.existsUserByUsername(form.getUsername())) {
            log.info("{} already exists", form.getEmail());
            throw UserAlreadyExistException.emailTaken();
        }

        if (userRepository.existsUserByUsername(form.getUsername())) {
            log.info("{} already exists", form.getUsername());
            throw UserAlreadyExistException.usernameTaken();
        }

        var user = userMapper.buildUserEntity(form);
        var profileRequest = new ProfileRequest();
        var defaultProfile = profileService.createProfile(profileRequest, user);

        user.setProfile(defaultProfile);
        user.setVerified(false);

        log.info("FormUserService#createUser {} is created", form.getUsername());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);

        log.info("User {} is deleted.", username);
    }
}
