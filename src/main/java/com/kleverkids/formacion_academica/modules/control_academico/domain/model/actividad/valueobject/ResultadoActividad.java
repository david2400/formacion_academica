package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoActividad {
    private Long estudianteId;
    private Long actividadId;
    private BigDecimal nota;
    private EstadoResultado estadoFinal;
    private Map<String, BigDecimal> notasPorCriterio;
    private String retroalimentacionGeneral;
    private Integer intentoNumero;
    private Long tiempoEmpleadoSegundos;

public enum EstadoResultado {
        PENDIENTE, EN_PROGRESO, COMPLETADO, APROBADO, RECHAZADO, EXPIRADO
    }

}
