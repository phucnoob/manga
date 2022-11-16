package uet.ppvan.mangareader.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import uet.ppvan.mangareader.dtos.AuthUserDetail;
import uet.ppvan.mangareader.utils.ValueMapper;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;


@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final long EXPIRE_DURATION = Duration.ofHours(24).toSeconds();

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        AuthUserDetail user = parseJWT(jwtAuthentication.getToken())
            .orElseThrow(() -> new BadCredentialsException("Jwt invalid failed."));
        jwtAuthentication.setUser(user);
        jwtAuthentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }

    public String generateJWT(AuthUserDetail user) {

        String json = ValueMapper.objectAsJson(user);

        return Jwts.builder()
            .setSubject(json)
            .setIssuer("USER")
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusSeconds(EXPIRE_DURATION)))
            .signWith(getKey())
            .compact();
    }

    private Optional<AuthUserDetail> parseJWT(String jwt) {
        try {
            var parser = jwtParser();
            var claims = parser.parseClaimsJws(jwt);
            String json = claims.getBody().getSubject();

            AuthUserDetail user = ValueMapper.jsonAsObject(AuthUserDetail.class, json);

            return Optional.of(user);
        } catch (JwtException ex) {
            System.out.println("Malform jwt");
        }

        return Optional.empty();
    }

    @Bean
    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    @Bean
    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build();
    }
}
