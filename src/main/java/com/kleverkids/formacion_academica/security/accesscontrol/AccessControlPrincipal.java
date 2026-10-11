package com.kleverkids.formacion_academica.security.accesscontrol;

import java.util.List;

/**
 * Identidad de un usuario de staff ya verificada por el JWT de access_control.
 *
 * @param userId     claim {@code sub}
 * @param employeeId claim {@code employeeId} (solo empleados; puede ser null)
 */
public record AccessControlPrincipal(Long userId, Long employeeId, String username, List<String> roles) {
}
