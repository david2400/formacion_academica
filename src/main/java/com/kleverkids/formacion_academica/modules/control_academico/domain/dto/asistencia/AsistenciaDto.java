package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaDto {
    private Long id;
    private Long claseId;
    private Long estudianteId;
    private LocalDateTime fechaRegistro;
    private boolean presente;

}
