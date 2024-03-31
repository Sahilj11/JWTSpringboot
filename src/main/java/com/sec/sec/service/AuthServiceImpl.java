package com.sec.sec.service;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sec.sec.model.AppUser;
import com.sec.sec.model.Role;
import com.sec.sec.repo.AppUserRepo;
import com.sec.sec.repo.RoleRepo;
import com.sec.sec.utils.JWTUtils;

import lombok.RequiredArgsConstructor;

/**
 * AuthServiceImpl
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepo appRepo;
    private final RoleRepo roleRepo;

    @Override
    public String login(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        var authenticate = authenticationManager.authenticate(authToken);
        return JWTUtils.generateToken(((UserDetails) authenticate.getPrincipal()).getUsername());
    }

    @Override
    public String signup(String name, String username, String password) {
        // checking if user exists
        if (appRepo.existsByUsername(username)) {
            throw new RuntimeException("User already exists");
        }

        // encoding password
        String encodedPassword = passwordEncoder.encode(password);
        Role roles = roleRepo.findByName("FREE").get();
        
        AppUser newUser = new AppUser();
        newUser.setName(name);
        newUser.setPassword(encodedPassword);
        newUser.setUsername(username);
        newUser.setAuthorities(Collections.singletonList(roles));
        appRepo.save(newUser);

        // using jwtUtils to generate jwt
        return JWTUtils.generateToken(username);
    }

}
