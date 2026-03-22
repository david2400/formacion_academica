package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public record ResultadoActividad(
    Long estudianteId,
    Long actividadId,
    BigDecimal nota,
    EstadoResultado estadoFinal,
    Map<String, BigDecimal> notasPorCriterio,
    String retroalimentacionGeneral,
    Integer intentoNumero,
    Long tiempoEmpleadoSegundos
) {
    public enum EstadoResultado {
        PENDIENTE, EN_PROGRESO, COMPLETADO, APROBADO, RECHAZADO, EXPIRADO
    }
}
