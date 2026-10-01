package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiltroLearningAssignmentDto {

    private Long sequenceId;
    private Long grupoId;
    private Long estudianteId;
    private EstadoLearningAssignment estado;

    public boolean estaVacio() {
        return sequenceId == null && grupoId == null && estudianteId == null && estado == null;
    }
}
