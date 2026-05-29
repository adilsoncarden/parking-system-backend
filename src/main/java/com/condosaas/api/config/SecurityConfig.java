package com.condosaas.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF temporalmente para poder hacer POST desde clientes externos
                // como este
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitir que cualquiera cree roles (solo para pruebas locales)
                        .requestMatchers("/api/roles/**").permitAll()
                        // El resto de rutas seguirán requiriendo autenticación
                        .anyRequest().authenticated());

        return http.build();
    }
}