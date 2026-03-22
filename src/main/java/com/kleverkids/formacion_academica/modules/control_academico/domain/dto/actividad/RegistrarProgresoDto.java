package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarProgresoDto {

    private BigDecimal nota;
    private String estadoFinal; // COMPLETADO, APROBADO, RECHAZADO
    private Map<String, BigDecimal> notasPorCriterio;
    private String retroalimentacionGeneral;
    private Integer intentoNumero;
    private Long tiempoEmpleadoSegundos;
    private Integer porcentajeAvance;
    private String respuesta; // Para respuestas abiertas
    private Map<String, Object> respuestasDetalle; // Para cuestionarios
}
