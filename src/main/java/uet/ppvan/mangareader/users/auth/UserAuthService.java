package uet.ppvan.mangareader.users.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.users.UserRepository;
import uet.ppvan.mangareader.users.exceptions.PasswordNotMatchException;

@RequiredArgsConstructor
@Service
public class UserAuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private final JwtAuthenticationProvider jwtAuthentication;

    public String validateUserLogin(AuthRequest request) {

        var user = repository.findUserByUsername(request.username())
            .orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exists", request.username())));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw PasswordNotMatchException.defaultValue();
        }

        AuthUserDetail authUser = new AuthUserDetail(user.getUsername(), user.getRole().getRole());

        return jwtAuthentication.generateJWT(authUser);
    }

}
