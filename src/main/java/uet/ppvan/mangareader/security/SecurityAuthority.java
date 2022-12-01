package uet.ppvan.mangareader.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uet.ppvan.mangareader.models.RoleEntity;

@RequiredArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final RoleEntity role;

    @Override
    public String getAuthority() {
        return role.getRole().name();
    }
}
