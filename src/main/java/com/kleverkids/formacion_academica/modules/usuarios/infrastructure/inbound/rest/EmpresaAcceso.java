package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.security.accesscontrol.AccessControlPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Aísla los datos por empresa: un usuario de staff solo opera sobre la empresa a la que pertenece.
 * La empresa llega en la petición (draco la toma de la sesión); aquí se verifica contra
 * access_control. Sin sesión de staff (modo observación) no se bloquea.
 */
@Component
class EmpresaAcceso {

    private final ProfesorConsultaPort consulta;
    /** false = modo abierto de desarrollo: no se verifica la empresa (FORMACION_VERIFICAR_EMPRESA). */
    private final boolean verificarEmpresa;

    EmpresaAcceso(ProfesorConsultaPort consulta,
                  @Value("${formacion.seguridad.verificar-empresa:false}") boolean verificarEmpresa) {
        this.consulta = consulta;
        this.verificarEmpresa = verificarEmpresa;
    }

    void verificar(Long empresaId) {
        if (empresaId == null) {
            throw new IllegalArgumentException("La empresa es obligatoria.");
        }
        if (!verificarEmpresa) {
            return;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AccessControlPrincipal p
                && !consulta.usuarioPerteneceAEmpresa(p.userId(), empresaId)) {
            throw new AccessDeniedException("No tienes acceso a esta empresa.");
        }
    }
}
