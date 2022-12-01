package uet.ppvan.mangareader.mappers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.dtos.RegisterForm;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.Role;
import uet.ppvan.mangareader.models.RoleEntity;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.RoleRepository;
import uet.ppvan.mangareader.repositories.UserRepository;


@Service
@Validated
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public User buildUserEntity(@Valid UserRequest request) {

        String password = passwordEncoder.encode(request.password());

        User user = new User();
        user.setId(null);
        user.setUsername(request.username());
        user.setPassword(password);
        user.setEmail(request.email());
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
        user.setRole(role);

        return user;
    }

    public User buildUserEntity(@Valid RegisterForm form) {

        String password = passwordEncoder.encode(form.getPassword());

        User user = new User();
        user.setId(null);
        user.setUsername(form.getUsername());
        user.setPassword(password);
        user.setEmail(form.getEmail());
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
        user.setRole(role);

        return user;
    }

    public User buildUserFromAuth(@Valid AuthUserDetail userDetail) {
        return userRepository
                   .findUserByUsername(userDetail.username())
                   .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
