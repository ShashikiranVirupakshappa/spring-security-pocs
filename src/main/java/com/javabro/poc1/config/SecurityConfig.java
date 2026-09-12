package com.javabro.poc1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder().username("user")
                .password(passwordEncoder().encode("password"))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                //line number 24 till 27 is not required since all are non expired, non locked and not disabled by default
                .authorities("read").build();
        return new InMemoryUserDetailsManager(userDetails);
    }*/

    @Bean
    //@DependsOn("DBUserDetailsService")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity.authorizeHttpRequests(request ->
                        //request.requestMatchers("/get").authenticated())
                        request.anyRequest().).oauth2ResourceServer()
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
