package uet.ppvan.mangareader.users.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtAuthenticationManager authenticationManager;

    @Value("${jwt.header.string}")
    private String headerString;

    @Value("${jwt.token.prefix}")
    private String headerPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
         * 1. Check if the token in the header
         * 2. Parse info from jwt token
         * 3. from that info, creat the authentication
         * 4. Pass to the manager
         * 5. Get it back then authenticate
         */

        /*
         * What should be in the jwt?
         * The username and the role
         *
         */
        try {
            String authorHeader = request.getHeader(headerString);
            String token = authorHeader.substring(headerPrefix.length());

            var jwtAuthentication = new JwtAuthentication(token);
            var authenticated = authenticationManager.authenticate(jwtAuthentication);

            if (authenticated.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            throw new BadCredentialsException("Authentication fails.");
        }

        filterChain.doFilter(request, response);
    }
}
