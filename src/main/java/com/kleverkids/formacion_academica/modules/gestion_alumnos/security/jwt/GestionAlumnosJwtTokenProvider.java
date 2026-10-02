package com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Emisor/validador del JWT propio de Estudiante/Acudiente. Aislado del
 * {@code JwtTokenProvider} de access_control a propósito (ver
 * GestionAlumnosJwtProperties): ni la clave de firma ni el formato de claims
 * son compartidos, porque Estudiante/Acudiente no son un {@code User} de
 * access_control.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GestionAlumnosJwtTokenProvider {

    private static final String CLAIM_TIPO = "tipo";
    private static final String CLAIM_NOMBRES = "nombres";
    private static final String CLAIM_APELLIDOS = "apellidos";

    private final GestionAlumnosJwtProperties properties;

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    public String generarToken(Long id, TipoSujetoAutenticado tipo, String nombres, String apellidos) {
        Instant now = Instant.now();
        Instant expiry = now.plus(properties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim(CLAIM_TIPO, tipo.name())
                .claim(CLAIM_NOMBRES, nombres)
                .claim(CLAIM_APELLIDOS, apellidos)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .issuer(properties.getIssuer())
                .signWith(signingKey())
                .compact();
    }

    public Instant expiracionDe(String token) {
        return claimsDe(token).getExpiration().toInstant();
    }

    /**
     * Parsea y valida el token (firma + expiración, ambas verificadas por
     * {@code parseSignedClaims}). Devuelve {@code null} si no es válido en
     * vez de propagar la excepción: el filtro que lo llama trata "sin token
     * válido" como anónimo, no como un error de la petición.
     */
    public GestionAlumnosPrincipal validarYExtraerPrincipal(String token) {
        try {
            Claims claims = claimsDe(token);
            Long id = Long.valueOf(claims.getSubject());
            TipoSujetoAutenticado tipo = TipoSujetoAutenticado.valueOf(claims.get(CLAIM_TIPO, String.class));
            return new GestionAlumnosPrincipal(id, tipo, claims.get(CLAIM_NOMBRES, String.class),
                    claims.get(CLAIM_APELLIDOS, String.class));
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Token de gestion_alumnos inválido: {}", e.getMessage());
            return null;
        }
    }

    private Claims claimsDe(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
