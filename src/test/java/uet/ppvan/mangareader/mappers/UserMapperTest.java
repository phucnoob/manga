package uet.ppvan.mangareader.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.Role;
import uet.ppvan.mangareader.models.RoleEntity;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.RoleRepository;
import uet.ppvan.mangareader.repositories.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    void init() {
//        when(encoder.encode(any()))
//            .then(func -> func.getArgument(0));
//        when(roleRepository.findByRole(Role.ROLE_ADMIN)).thenReturn(adminRole);
    }


    @Test
    @DisplayName("Run normal in happy case")
    void shouldConvertDataInHappyCase() {
        var request = new UserRequest(
            "username",
            "email",
            "password",
            "password"
        );

        User user = new User();
        user.setPassword("encoded password");
        user.setId(null);
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setRole(new RoleEntity(Role.ROLE_USER));
        RoleEntity userRole = new RoleEntity();
        userRole.setRole(Role.ROLE_USER);

        when(roleRepository.findByRole(Role.ROLE_USER)).thenReturn(userRole);
        when(encoder.encode(any())).thenReturn("encoded password");

        Assertions.assertThat(userMapper.buildUserEntity(request))
            .usingRecursiveComparison()
            .isEqualTo(user);
    }

    @Test
    @DisplayName("If User not found then exception")
    void shouldThrowIfNotFound() {
        AuthUserDetail detail = new AuthUserDetail(12, "username", Role.ROLE_USER);
        User user = new User();
        user.setPassword("password");
        user.setId(null);
        user.setEmail("email");
        user.setUsername(detail.username());
        user.setRole(new RoleEntity(Role.ROLE_USER));


        when(userRepository.findUserByUsername(detail.username()))
            .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userMapper.buildUserFromAuth(detail))
            .isInstanceOf(UsernameNotFoundException.class);
    }
}