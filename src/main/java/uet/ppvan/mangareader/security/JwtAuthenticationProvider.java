package uet.ppvan.mangareader.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.utils.JwtUtils;


@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        AuthUserDetail user = JwtUtils.parseJWT(AuthUserDetail.class, jwtAuthentication.getToken());
        jwtAuthentication.setUser(user);
        jwtAuthentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
