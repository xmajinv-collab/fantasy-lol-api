package com.fantasylol.fantasy_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // API REST → sin CSRF
            .csrf(csrf -> csrf.disable())

            // permitir CORS para React
            .cors(cors -> {})

            // configuración de endpoints
            .authorizeHttpRequests(auth -> auth

                    // auth libre
                    .requestMatchers("/api/auth/**").permitAll()

                    // swagger si lo usas
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()

                    // todo lo demás requiere login
                    .anyRequest().authenticated()
            )

            // API REST sin sesiones
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}