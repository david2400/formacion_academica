package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosPrincipal;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.TipoSujetoAutenticado;
import com.kleverkids.formacion_academica.security.accesscontrol.AccessControlPrincipal;
import com.kleverkids.formacion_academica.security.accesscontrol.RolNormalizado;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Reglas de acceso del módulo de Feedback, usadas desde {@code @PreAuthorize}
 * ({@code @feedbackSeguridad.administracion()}) y por los controladores para
 * resolver QUIÉN pide (el id nunca sale de la URL).
 *
 * <p>Ocultar botones en el frontend no protege nada (CLAUDE.md §83): esto sí.
 */
@Component("feedbackSeguridad")
@RequiredArgsConstructor
public class FeedbackSeguridad {

    private final FeedbackSeguridadProperties properties;

    public boolean administracion() {
        return tieneAlguno(properties.getRolesAdministracion());
    }

    public boolean docente() {
        return tieneAlguno(properties.getRolesDocente());
    }

    public boolean administracionODocente() {
        return administracion() || docente();
    }

    public boolean estudiante() {
        return estudianteActualOpcional() != null;
    }

    /** Id del estudiante autenticado (JWT de gestion_alumnos). */
    public Long estudianteActual() {
        Long id = estudianteActualOpcional();
        if (id == null) {
            throw new AccessDeniedException("Se requiere una sesión de estudiante.");
        }
        return id;
    }

    /**
     * Id de docente para restringir resultados, o null si quien consulta es de
     * administración (ve todo). Un docente siempre queda limitado a sus clases,
     * aunque mande otro profesor_id en el query.
     */
    public Long docenteRestringido() {
        if (administracion()) {
            return null;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (docente() && auth != null && auth.getPrincipal() instanceof AccessControlPrincipal p) {
            Long id = properties.getIdentificadorDocente() == FeedbackSeguridadProperties.IdentificadorDocente.EMPLOYEE_ID
                    ? p.employeeId() : p.userId();
            if (id != null) {
                return id;
            }
        }
        throw new AccessDeniedException("No tienes acceso a estos resultados.");
    }

    private Long estudianteActualOpcional() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof GestionAlumnosPrincipal p
                && p.tipo() == TipoSujetoAutenticado.ESTUDIANTE) {
            return p.id();
        }
        return null;
    }

    private static boolean tieneAlguno(List<String> roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        for (GrantedAuthority a : auth.getAuthorities()) {
            for (String rol : roles) {
                if (RolNormalizado.autoridad(rol).equals(a.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }
}
