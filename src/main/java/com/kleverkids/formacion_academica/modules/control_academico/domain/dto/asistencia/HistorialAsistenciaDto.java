package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialAsistenciaDto {
    private Long estudianteId;
    private Long claseId;
    private LocalDate desde;
    private LocalDate hasta;
    private List<AsistenciaDto> asistencias;

}
