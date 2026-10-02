package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Respuesta de login para Estudiante o Acudiente. No hay un "User" común
 * entre ambos (a diferencia de access_control), así que el id y el tipo
 * identifican de forma explícita a quién pertenece el token — el consumidor
 * (portal_clientes) decide qué hacer según {@code tipo}.
 *
 * <p>{@code apellidos} viene ya compuesto por el servicio: Estudiante separa
 * primerApellido/segundoApellido, Acudiente tiene un único campo apellidos —
 * unificarlo aquí evita que el DTO de login tenga que modelar esa diferencia.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String accessToken;
    private String tokenType;
    private Instant expiresAt;

    /** "ESTUDIANTE" o "ACUDIENTE" — mismo valor que el claim "tipo" del token. */
    private String tipo;

    private Long id;
    private String nombres;
    private String apellidos;
}
