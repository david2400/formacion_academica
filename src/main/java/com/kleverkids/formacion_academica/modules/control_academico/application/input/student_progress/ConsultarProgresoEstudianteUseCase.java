package com.kleverkids.formacion_academica.modules.control_academico.application.input.student_progress;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;

public interface ConsultarProgresoEstudianteUseCase {

    StudentSequenceProgress consultar(Long estudianteId, Long sequenceId);
}
