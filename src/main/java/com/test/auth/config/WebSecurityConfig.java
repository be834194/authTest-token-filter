package com.test.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    private final AuthorizeFilter authorizeFilter;

    private final AuthenticationManager authenticationManager;

    public WebSecurityConfig(AuthenticationFilter authenticationFilter,
                             AuthorizeFilter authorizeFilter,
                             AuthenticationManager authenticationManager){
        this.authenticationFilter = authenticationFilter;
        this.authorizeFilter = authorizeFilter;
        this.authenticationManager = authenticationManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers( "/login", "/account").permitAll()
                .anyRequest().authenticated());
        http.authenticationManager(authenticationManager);
        http.addFilter(authenticationFilter)
            .addFilterBefore(authorizeFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
