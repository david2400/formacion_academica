package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTematicaDto {
    private Long id;
    private Long activityId;
    private Long tematicaId;
}
