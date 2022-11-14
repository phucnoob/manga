package uet.ppvan.mangareader.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.users.*;
import uet.ppvan.mangareader.users.exceptions.UserAlreadyExistException;

@RequiredArgsConstructor
@Service
public class UserRegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public void createUser(UserRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw UserAlreadyExistException.emailTaken();
        } else if (userRepository.existsUserByUsername(request.getUsername())) {
            throw UserAlreadyExistException.usernameTaken();
        }

        User user = toEntity(request);
        userRepository.save(user);
    }

    private User toEntity(UserRequest request) {

        String password = encoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(password);
        user.setEmail(request.getEmail());
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER);
        user.setRole(role);

        return user;
    }

}
