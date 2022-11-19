package uet.ppvan.mangareader.utils;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private void setSecretKey(String secretKey) {
        JwtUtils.SECRET_KEY = secretKey;
    }

    private static String SECRET_KEY; // default

    @SuppressWarnings("unused")
    public static String generateJWT(Object obj) {
        String json = ValueMapper.objectAsJson(obj);

        return Jwts.builder()
        .setSubject(json)
        .setIssuer("Unknown")
        .setIssuedAt(Date.from(Instant.now()))
        .signWith(getKey())
        .compact();
    }

    public static String generateJWT(Object obj, Duration expiredTime) {
        String json = ValueMapper.objectAsJson(obj);

        return Jwts.builder()
        .setSubject(json)
        .setIssuer("Unknown")
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(expiredTime)))
        .signWith(getKey())
        .compact();
    }

    public static <T> T parseJWT(Class<T> type, String jwt) throws ExpiredJwtException, MalformedJwtException {
        var parser = jwtParser();
        var claims = parser.parseClaimsJws(jwt);
        String json = claims.getBody().getSubject();

        return ValueMapper.jsonAsObject(type, json);
    }

    @Bean
    private static Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    @Bean
    private static JwtParser jwtParser() {
        return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build();
    }
}
