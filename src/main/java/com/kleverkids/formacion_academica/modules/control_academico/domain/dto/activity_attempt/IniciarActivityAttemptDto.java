package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IniciarActivityAttemptDto {

    private Long activityId;

    /** NULL = práctica libre sin asignación formal. */
    private Long assignmentId;

    private Long estudianteId;
}
