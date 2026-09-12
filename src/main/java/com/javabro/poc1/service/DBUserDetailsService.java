package com.javabro.poc1.service;

import com.javabro.poc1.entity.User;
import com.javabro.poc1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DBUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DBUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername");
        User user = userRepository.findByUsernameAndIsActive(username, true)
                .orElseThrow(() -> new RuntimeException("username with " + username + " not found"));
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        List<String> authorities = Arrays.asList(user.getAuthorities().split(","));
        authorities.stream().map(SimpleGrantedAuthority::new).forEach(grantedAuthorityList::add);
        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).authorities(grantedAuthorityList).build();
    }
}
