package uet.ppvan.mangareader.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.services.JWTService;


//@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JWTService defaultJWTService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        AuthUserDetail user = defaultJWTService.parseJWT(AuthUserDetail.class, jwtAuthentication.getToken());
        jwtAuthentication.setUser(user);
        jwtAuthentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
