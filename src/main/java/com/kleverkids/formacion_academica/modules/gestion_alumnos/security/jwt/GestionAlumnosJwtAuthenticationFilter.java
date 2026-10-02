package com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Puebla el {@code SecurityContextHolder} a partir del JWT propio de
 * Estudiante/Acudiente, cuando viene uno en el header Authorization.
 *
 * <p>Esto NO reemplaza el filtro de validación del JWT de access_control que
 * sigue faltando para DOCENTE/ADMINISTRADOR (riesgo de seguridad transversal
 * documentado en la Fase 1 del módulo de Actividades) — es un mecanismo
 * aparte, acotado a las rutas que consume un Estudiante/Acudiente. Si no hay
 * token o no es válido, la petición sigue como anónima: el filtro no
 * rechaza nada por sí mismo (SpringSecurityConfig sigue en
 * {@code anyRequest().permitAll()}), solo habilita que {@code @PreAuthorize}
 * pueda evaluar roles cuando el token sí es válido.
 */
@Component
@RequiredArgsConstructor
public class GestionAlumnosJwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final GestionAlumnosJwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HEADER);

        if (header != null && header.startsWith(PREFIX) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = header.substring(PREFIX.length());
            GestionAlumnosPrincipal principal = tokenProvider.validarYExtraerPrincipal(token);

            if (principal != null) {
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + principal.tipo().name()));
                var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
