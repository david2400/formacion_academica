package com.kleverkids.formacion_academica.modules.control_academico.application.input.student_progress;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;

import java.util.List;

/** Vista docente: progreso de todos los estudiantes con asignación activa en una secuencia. */
public interface ConsultarProgresoSequenceUseCase {

    List<StudentSequenceProgress> consultarPorSecuencia(Long sequenceId);
}
