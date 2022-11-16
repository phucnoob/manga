package uet.ppvan.mangareader.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import uet.ppvan.mangareader.dtos.AuthUserDetail;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
@Setter
public class JwtAuthentication implements Authentication {

    private final String token;

    // user maybe null.
    // The provider will set the user from info parse from token,
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
