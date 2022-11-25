package uet.ppvan.mangareader.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uet.ppvan.mangareader.security.JwtFilter;

/**
 * Spring security authorization config.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.csrf().disable();

        return http
                   .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                   .authorizeRequests()
//        .mvcMatchers("/api/v1/users/profile").authenticated()
//        .mvcMatchers("/api/v1/users/login", "/api/v1/users/register", "/api/v1/users/verify").permitAll()
//            .mvcMatchers(HttpMethod.GET, "/**").permitAll()
//            .anyRequest().authenticated()
                   .anyRequest().permitAll()
            .and()
            .build();

    }
}
