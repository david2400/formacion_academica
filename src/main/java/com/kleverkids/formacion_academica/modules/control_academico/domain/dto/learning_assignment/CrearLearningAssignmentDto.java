package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearLearningAssignmentDto {

    private Long sequenceId;

    /** Exactamente uno de grupoId/estudianteId debe venir informado. */
    private Long grupoId;
    private Long estudianteId;

    private Long assignedBy;
    private LocalDateTime availableFrom;
    private LocalDateTime dueAt;
    private boolean allowLateSubmission;
    private boolean lockAfterDue;
}
