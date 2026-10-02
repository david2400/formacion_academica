 package com.kleverkids.formacion_academica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final GestionAlumnosJwtAuthenticationFilter gestionAlumnosJwtAuthenticationFilter;

    /**
     * Mismo mecanismo (BCrypt) que ya usa access_control para las contraseñas
     * de User (ver EmployeeAuthenticationStrategy). Se usa para hashear la
     * contraseña propia de Acudiente/Estudiante — nunca se guarda en texto
     * plano ni se puede revertir a partir del hash.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] PUBLIC_ENDPOINTS = {
            "/v2/api-docs",
            "/webjars/**",
            "/resources/**",
            "/static/**",
            "/public/**",
            "/vendor/**",
            "/error",
            // OpenAPI / Swagger
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().permitAll()
                )
                // Puebla el SecurityContext a partir del JWT propio de Estudiante/
                // Acudiente cuando viene uno (ver GestionAlumnosJwtAuthenticationFilter).
                // No sustituye el filtro de validación del JWT de access_control que
                // sigue faltando para DOCENTE/ADMINISTRADOR (riesgo ya documentado).
                .addFilterBefore(gestionAlumnosJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/v3/api-docs/**", config);
        source.registerCorsConfiguration("/swagger-ui/**", config);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
