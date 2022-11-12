package uet.ppvan.mangareader.users.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uet.ppvan.mangareader.users.Role;


@RequiredArgsConstructor
public class SecurityRole implements GrantedAuthority {

    private final Role role;

    @Override
    public String getAuthority() {
        return role.getRole();
    }
}
