package com.sec.sec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationFilter jwtFilter;
    private final CustomAuthExceptionHandler authExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity hSecurity) throws Exception{
        // disable cors
        hSecurity.cors(AbstractHttpConfigurer::disable);

        // disable csrf
        hSecurity.csrf(AbstractHttpConfigurer::disable);

        // api
        hSecurity.authorizeHttpRequests(requestMatcher-> 
                requestMatcher.requestMatchers("/api/auth/login/**").permitAll()
                .requestMatchers("/api/auth/sign-up/**").permitAll()
                .anyRequest().authenticated());

        // exception handling
        hSecurity.exceptionHandling(exception-> 
                exception.authenticationEntryPoint(authenticationEntryPoint));

        // Set SessionCreationPolicy -> STATELESS
        hSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        hSecurity.formLogin(login-> login.failureHandler(authExceptionHandler));

        //jwt
        hSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return hSecurity.build();
    }
    
}
