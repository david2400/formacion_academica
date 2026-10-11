package com.kleverkids.formacion_academica.security;

import com.essenza.security.AccessControlTokenValidator;
import com.essenza.security.EssenzaSecurityProperties;
import com.essenza.security.ObservacionAccesoFilter;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosJwtAuthenticationFilter;
import com.kleverkids.formacion_academica.security.accesscontrol.AccessControlJwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Seguridad de formacion_academica (Fase 4 del plan).
 *
 * <p>Dos tipos de token pueblan el SecurityContext:
 * <ul>
 *   <li>Estudiante/acudiente: {@link GestionAlumnosJwtAuthenticationFilter} (token propio y, desde D9,
 *       el central de access_control con {@code user_type} de estudiante/acudiente).</li>
 *   <li>Personal (docentes, coordinacion...): {@link AccessControlJwtAuthenticationFilter}, que valida con
 *       essenza-security y deja los roles/permisos del contexto para los {@code @PreAuthorize}.</li>
 * </ul>
 *
 * <p>Arranca en <b>modo observacion</b> ({@code FORMACION_ENFORCE_AUTH=false}): no bloquea nada, pero
 * registra en el log cada ruta llamada sin token valido. Cuando el log este limpio (o las rutas que
 * deben ser publicas esten en {@code FORMACION_RUTAS_PUBLICAS[_GET]}) se activa con
 * {@code FORMACION_ENFORCE_AUTH=true}. Respaldo de la version anterior: target/SpringSecurityConfig.java.antes-f4.
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    /** Documentacion, errores y el login propio de estudiantes/acudientes: siempre publicos. */
    private static final List<String> SIEMPRE_PUBLICAS = List.of(
            "/error",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/resources/**",
            "/static/**",
            "/public/**",
            "/vendor/**",
            "/api/kleverkids/gestion-alumnos/auth/**");

    private final GestionAlumnosJwtAuthenticationFilter gestionAlumnosJwtAuthenticationFilter;

    @Value("${cors.allowed-origins:*}")
    private List<String> allowedOrigins;

    public SpringSecurityConfig(GestionAlumnosJwtAuthenticationFilter gestionAlumnosJwtAuthenticationFilter) {
        this.gestionAlumnosJwtAuthenticationFilter = gestionAlumnosJwtAuthenticationFilter;
    }

    /**
     * Mismo mecanismo (BCrypt) que ya usa access_control para las contraseñas de User. Se usa para
     * hashear la contraseña propia de Acudiente/Estudiante.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * El filtro de estudiantes es @Component, y Spring Boot registra todo Filter-bean tambien como filtro
     * de servlet. Corria entonces dos veces: fuera de la cadena (y su autenticacion se perdia al entrar a
     * ella) y dentro, donde OncePerRequestFilter ya lo saltaba. Se desactiva el registro de servlet para
     * que solo corra dentro de la cadena de seguridad.
     */
    @Bean
    public FilterRegistrationBean<GestionAlumnosJwtAuthenticationFilter> gestionAlumnosFiltroSoloEnCadena(
            GestionAlumnosJwtAuthenticationFilter filtro) {
        FilterRegistrationBean<GestionAlumnosJwtAuthenticationFilter> registro = new FilterRegistrationBean<>(filtro);
        registro.setEnabled(false);
        return registro;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessControlTokenValidator validator,
                                                   EssenzaSecurityProperties props) throws Exception {
        List<String> publicas = new ArrayList<>(SIEMPRE_PUBLICAS);
        publicas.addAll(props.getRutasPublicas());
        List<String> publicasGet = props.getRutasPublicasGet();

        // Se crea aqui (no como @Component) para que solo corra dentro de la cadena de seguridad.
        AccessControlJwtAuthenticationFilter personal = new AccessControlJwtAuthenticationFilter(validator);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Sin token valido: 401 (sin esto Spring respondia 403).
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(gestionAlumnosJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(personal, GestionAlumnosJwtAuthenticationFilter.class);

        if (props.isEnforceAuthentication()) {
            http.authorizeHttpRequests(auth -> {
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                auth.requestMatchers(publicas.toArray(String[]::new)).permitAll();
                if (!publicasGet.isEmpty()) {
                    auth.requestMatchers(HttpMethod.GET, publicasGet.toArray(String[]::new)).permitAll();
                }
                auth.anyRequest().authenticated();
            });
        } else {
            log.warn("Modo observacion: formacion_academica acepta peticiones SIN token y solo las registra."
                    + " Revisar el log y activar FORMACION_ENFORCE_AUTH=true.");
            http.addFilterAfter(new ObservacionAccesoFilter(publicas, publicasGet), AccessControlJwtAuthenticationFilter.class)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }
        return http.build();
    }

    /** Sin credenciales (la API usa el encabezado Authorization). Origenes por {@code CORS_ALLOWED_ORIGINS}. */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
