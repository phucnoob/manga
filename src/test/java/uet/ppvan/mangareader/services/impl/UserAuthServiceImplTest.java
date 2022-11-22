package uet.ppvan.mangareader.services.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.exceptions.PasswordNotMatchException;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.UserRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceImplTest {

    @Mock
    private UserRepository repository;

    @Spy
    private PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @InjectMocks
    private UserAuthServiceImpl userAuthService;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("Throw exception if user not exist.")
    void shouldThrowExceptionIfUserNotFound() {
        AuthRequest request = new AuthRequest("test", "pass");
        Mockito.when(repository.findUserByUsername(request.username()))
            .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userAuthService.validateUserLogin(request))
            .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    @DisplayName("Throw exception if user enter wrong password.")
    void shouldThrowExceptionIfPasswordNotMatch() {
        AuthRequest request = new AuthRequest("test", "pass");
        Mockito.when(repository.findUserByUsername(request.username()))
            .thenReturn(Optional.of(Mockito.mock(User.class)));

        Assertions.assertThatThrownBy(() -> userAuthService.validateUserLogin(request))
            .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    @DisplayName("Throw exception if user enter wrong password.")
    void shouldReturnValidJWT() {
        AuthRequest request = new AuthRequest("test", "pass");
//        User user = ValueMapper.toUserEntity(request);

        Mockito.when(repository.findUserByUsername(request.username()))
            .thenReturn(Optional.of(Mockito.mock(User.class)));

        Assertions.assertThatThrownBy(() -> userAuthService.validateUserLogin(request))
            .isInstanceOf(PasswordNotMatchException.class);
    }


    @Test
    void verifyEmail() {
    }

    @Test
    void sendPasswordResetEmail() {
    }

    @Test
    void resetPassword() {
    }
}