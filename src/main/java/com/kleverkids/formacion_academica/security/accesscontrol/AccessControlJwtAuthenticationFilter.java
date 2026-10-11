package com.kleverkids.formacion_academica.security.accesscontrol;

import com.essenza.security.AccessControlTokenValidator;
import com.essenza.security.TokenValidado;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component; // TODO(permisos)
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Puebla el SecurityContext con el usuario de staff cuando la petición trae un token VÁLIDO
 * de access_control. La verificación (RS256 del servidor de autorización vía JWKS y, durante
 * la transición, el HMAC viejo; emisor, audiencia, typ y vigencia) la hace la librería
 * essenza-security ({@code essenza.security.*} en application.properties). El token de
 * estudiante/acudiente (otro emisor) no pasa por aquí: lo atiende GestionAlumnosJwtAuthenticationFilter.
 *
 * <p>El filtro NO rechaza peticiones por sí mismo ({@code anyRequest().permitAll()} sigue igual):
 * solo hace que {@code @PreAuthorize} pueda evaluar roles. Autoridades: {@code ROLE_<ROL>} por
 * cada rol y cada permiso del contexto tal cual. Los tokens de servicio se ignoran.
 */
@Slf4j
// TODO(permisos): reactivar. Sin @Component el filtro no se crea ni se registra
// (Spring Boot registraría automáticamente cualquier Filter que sea bean).
// @Component
public class AccessControlJwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";

    /** Motivo por el que el Bearer no se aceptó; lo usa el 401 para dejar un log útil. */
    public static final String ATRIBUTO_RECHAZO = AccessControlJwtAuthenticationFilter.class.getName() + ".rechazo";

    private final AccessControlTokenValidator validator;

    public AccessControlJwtAuthenticationFilter(AccessControlTokenValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(PREFIX)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            autenticar(header.substring(PREFIX.length()), request);
        }
        chain.doFilter(request, response);
    }

    private void autenticar(String token, HttpServletRequest request) {
        try {
            TokenValidado t = validator.validar(token);
            if (t.servicio() || t.userId() == null) {
                return;
            }
            List<GrantedAuthority> autoridades = new ArrayList<>();
            t.roles().forEach(r -> autoridades.add(new SimpleGrantedAuthority(RolNormalizado.autoridad(r))));
            if (t.permisosEnToken()) {
                t.permissions().forEach(p -> autoridades.add(new SimpleGrantedAuthority(p)));
            } else {
                // Token viejo: sus permisos no son por contexto, pero es lo que habia antes.
                Object viejos = t.claims().get("permissions");
                if (viejos instanceof java.util.Collection<?> c) {
                    c.forEach(p -> autoridades.add(new SimpleGrantedAuthority(String.valueOf(p))));
                }
            }
            AccessControlPrincipal principal = new AccessControlPrincipal(t.userId(), numero(t.claims().get("employeeId")),
                    (String) t.claims().get("username"), t.roles());
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(principal, null, autoridades));
        } catch (JwtException | IllegalArgumentException e) {
            // Token de otro emisor (p. ej. el de estudiante), vencido o alterado: la petición sigue anónima.
            log.debug("Token de access_control no válido: {}", e.getMessage());
            request.setAttribute(ATRIBUTO_RECHAZO, e.getClass().getSimpleName());
        }
    }

    private static Long numero(Object valor) {
        if (valor instanceof Number n) {
            return n.longValue();
        }
        if (valor instanceof String s && !s.isBlank()) {
            try {
                return Long.valueOf(s);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
