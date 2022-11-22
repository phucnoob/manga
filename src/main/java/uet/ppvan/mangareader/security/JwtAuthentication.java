package uet.ppvan.mangareader.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import uet.ppvan.mangareader.dtos.AuthUserDetail;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom Authentication to use JWT authentication method.
 * @see JwtFilter
 * @see JwtAuthenticationManager
 * @see JwtAuthenticationProvider
 * @see uet.ppvan.mangareader.utils.JwtUtils
 */
@RequiredArgsConstructor
@Getter
@Setter
public class JwtAuthentication implements Authentication {

    /**
     * The token passed by client
     */
    private final String token;

    /**
     * Object represent user info.
     * user can be null (not authenticated).
     * @implNote The provider will set the user with info parsed from token (if success).
     */
    //
    private AuthUserDetail user;
    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> user.role().name());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return "JWT authentication";
    }
}
