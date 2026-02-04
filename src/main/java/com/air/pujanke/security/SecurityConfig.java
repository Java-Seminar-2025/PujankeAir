package com.air.pujanke.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final SecurityUserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/register", "/login").permitAll()
                .requestMatchers("/home", "/about-us", "/search").permitAll()
                .requestMatchers("/css/**", "/js/**", "/api/**", "/img/**").permitAll()
                .anyRequest().authenticated()
        ).formLogin((httpSec) -> httpSec
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/home", true)
        ).logout((httpSec) -> httpSec
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
        ).exceptionHandling((httpSec) -> httpSec
                .accessDeniedPage("/home?unauthorized=true")
        );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
