package uet.ppvan.mangareader.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.dtos.UserRequest;
import uet.ppvan.mangareader.models.Role;
import uet.ppvan.mangareader.models.RoleEntity;
import uet.ppvan.mangareader.models.User;
import uet.ppvan.mangareader.repositories.RoleRepository;
import uet.ppvan.mangareader.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public User buildUserEntity(UserRequest request) {

        String password = passwordEncoder.encode(request.password());

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(password);
        user.setEmail(request.email());
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
        user.setRole(role);

        return user;
    }

    public User buildUserFromAuth(AuthUserDetail userDetail) {
        return userRepository
                   .findUserByUsername(userDetail.username())
                   .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
