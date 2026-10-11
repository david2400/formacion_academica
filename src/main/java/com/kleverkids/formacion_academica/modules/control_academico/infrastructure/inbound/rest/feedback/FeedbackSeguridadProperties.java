package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Qué roles de access_control cuentan como administración y como docencia
 * para Feedback (prefijo {@code feedback.seguridad}). Configurable porque los
 * códigos de rol son datos de access_control, no del código: si allá se
 * llaman distinto, se ajusta aquí sin desplegar.
 */
@Data
@Component
@ConfigurationProperties(prefix = "feedback.seguridad")
public class FeedbackSeguridadProperties {

    /** Pueden crear formularios, aplicarlos y ver todos los resultados. */
    private List<String> rolesAdministracion = List.of("ADMINISTRADOR", "COORDINADOR");

    /** Ven resultados agregados SOLO de sus clases. */
    private List<String> rolesDocente = List.of("DOCENTE");

    /**
     * Qué identificador del token corresponde a {@code clase_profesores.profesor_id}:
     * USER_ID (claim sub) o EMPLOYEE_ID (claim employeeId).
     */
    private IdentificadorDocente identificadorDocente = IdentificadorDocente.USER_ID;

    public enum IdentificadorDocente {USER_ID, EMPLOYEE_ID}
}
