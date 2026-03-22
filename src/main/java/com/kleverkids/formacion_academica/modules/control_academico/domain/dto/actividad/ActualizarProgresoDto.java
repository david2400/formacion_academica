package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarProgresoDto {

    private Integer porcentajeAvance;
    private String estado; // EN_PROGRESO, COMPLETADO, etc.
    private Long tiempoEmpleadoSegundos;
    private String ultimaInteraccion; // Descripción de la última interacción
}
