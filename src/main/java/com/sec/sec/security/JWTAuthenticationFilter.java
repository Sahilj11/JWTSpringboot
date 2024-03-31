package com.sec.sec.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sec.sec.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // fetch token from request
        var jwtTokenOptional = getTokenFromReq(request);

        jwtTokenOptional.ifPresent(jwtToken -> {
            if (JWTUtils.validateToken(jwtToken)) {
                var usernameOptional = JWTUtils.getUserName(jwtToken);

                usernameOptional.ifPresent(username -> {
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // set the token to security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }
        });

        filterChain.doFilter(request, response);
    }

    public Optional<String> getTokenFromReq(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
