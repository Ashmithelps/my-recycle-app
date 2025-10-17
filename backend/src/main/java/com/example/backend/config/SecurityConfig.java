package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable for development/testing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // allow register/login
                .anyRequest().authenticated() // protect other routes
            )
            .httpBasic(httpBasic -> {}) // enable basic auth for testing
            .formLogin(form -> form.disable()); // disable default login form

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
