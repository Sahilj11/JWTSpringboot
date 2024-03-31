package com.sec.sec.service.userdetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sec.sec.model.AppUser;
import com.sec.sec.model.Role;
import com.sec.sec.repo.AppUserRepo;

import lombok.AllArgsConstructor;

/**
 * UserdetailsImpl
 */
@Service
@AllArgsConstructor
public class UserdetailsImpl implements UserDetailsService {

    private final AppUserRepo appRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return new User(appUser.getUsername(), appUser.getPassword(), mapRoles(appUser.getRole()));
    }

    private Collection<GrantedAuthority> mapRoles(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
