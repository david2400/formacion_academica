package com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt;

/**
 * Principal autenticado que el filtro pone en el {@code SecurityContext}:
 * el id de Estudiante o Acudiente (según {@code tipo}) que hizo la petición.
 * No es un {@code UserDetails} de Spring Security porque no hay credenciales
 * que recargar ni cuenta que bloquear/expirar — es solo la identidad ya
 * verificada por el JWT.
 */
public record GestionAlumnosPrincipal(Long id, TipoSujetoAutenticado tipo, String nombres, String apellidos) {
}
