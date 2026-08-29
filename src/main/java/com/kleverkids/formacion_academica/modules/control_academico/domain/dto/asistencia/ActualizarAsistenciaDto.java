package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarAsistenciaDto {
    private Long asistenciaId;
    private Long estudianteId;
    private Long claseId;
    private LocalDate fecha;
    private String estado;
    private String observaciones;

}
