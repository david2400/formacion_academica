package com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Configuración del JWT propio de Estudiante/Acudiente. Deliberadamente
 * separado de cualquier configuración de access_control: este token no lo
 * valida ni lo emite ese servicio, así que comparte prefijo con nada de lo
 * que ya existe (evita que alguien asuma que ambos JWT son intercambiables).
 */
@Data
@Component
@ConfigurationProperties(prefix = "gestion-alumnos.jwt")
public class GestionAlumnosJwtProperties {

    private String secret = "change-me-gestion-alumnos-jwt-secret-key-dev-only";

    private Duration accessTokenExpiration = Duration.ofHours(12);

    private String issuer = "formacion-academica-gestion-alumnos";
}
