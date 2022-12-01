package uet.ppvan.mangareader.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uet.ppvan.mangareader.models.Profile;
import uet.ppvan.mangareader.models.User;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> user.getRole().getRole().name());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getVerified();
    }

    public Profile getProfile() {
        return user.getProfile();
    }
}
