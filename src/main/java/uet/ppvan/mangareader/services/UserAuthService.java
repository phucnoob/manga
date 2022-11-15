package uet.ppvan.mangareader.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uet.ppvan.mangareader.dtos.AuthRequest;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.exceptions.PasswordNotMatchException;
import uet.ppvan.mangareader.repositories.UserRepository;
import uet.ppvan.mangareader.security.JwtAuthenticationProvider;

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
