package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenTematicaDto {
    private Long id;
    private Long examenId;
    private Long tematicaId;
}
