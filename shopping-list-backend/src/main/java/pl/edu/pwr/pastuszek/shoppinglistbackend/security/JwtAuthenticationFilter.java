package pl.edu.pwr.pastuszek.shoppinglistbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String jwtHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;


        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {        // check if request has a jwt
            filterChain.doFilter(request, response);    // pass execution to the next filter
            return;
        }

        jwt = jwtHeader.substring(7);       // start of the JWT
        username = jwtService.getUsername(jwt);      // get username (email) from the token, throws JWT exception if token is invalid

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {       // check if user is already authenticated
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwt, userDetails)){
                // update SecurityContextHolder
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);        // pass execution to the next filter
    }
}
