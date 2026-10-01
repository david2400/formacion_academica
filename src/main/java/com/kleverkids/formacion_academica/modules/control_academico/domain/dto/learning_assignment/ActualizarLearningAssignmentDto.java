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
public class ActualizarLearningAssignmentDto {

    private Long id;
    private LocalDateTime availableFrom;
    private LocalDateTime dueAt;
    private Boolean allowLateSubmission;
    private Boolean lockAfterDue;
}
