package uet.ppvan.mangareader.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Spring security authorization config.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();


        return http
                   .authorizeHttpRequests()
                   .requestMatchers(
                       "/user/login",
                       "/user/password-reset-request",
                       "/user/password-reset",
                       "/user/login-error",
                       "/user/register",
                       "/user/verify"
                   )
                   .permitAll()

                   .requestMatchers("/js/**", "/css/**", "/images/**")
                   .permitAll()

                   .requestMatchers("/user/*")
                   .authenticated()
                   .requestMatchers(HttpMethod.GET, "/**")
                   .permitAll()

                   .anyRequest()
                   .authenticated()

                   .and()
                   .formLogin()
                   .loginPage("/user/login")
                   .failureForwardUrl("/user/login-error")
                   .defaultSuccessUrl("/")

                   .and()
                   .logout()
                   .deleteCookies("JSESSIONID")
                   .logoutUrl("/user/logout")
                   .logoutSuccessUrl("/")

                   .and()
                   .rememberMe()
                   .and()
                   .build();

    }
}
