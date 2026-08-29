package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteExamenDto {
    private Long id;
    private Long examenId;
    private Long estudianteId;
    private LocalDateTime asignadoEn;

}
