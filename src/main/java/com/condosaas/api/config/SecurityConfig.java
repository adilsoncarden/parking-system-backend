package com.condosaas.api.config;

import org.springframework.context.annotation.Configuration;
import com.condosaas.api.security.JwtFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

}
