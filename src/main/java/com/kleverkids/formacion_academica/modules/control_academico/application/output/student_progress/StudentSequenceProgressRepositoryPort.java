package com.kleverkids.formacion_academica.modules.control_academico.application.output.student_progress;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;

import java.util.List;
import java.util.Optional;

public interface StudentSequenceProgressRepositoryPort {

    Optional<StudentSequenceProgress> obtenerPorEstudianteYSecuencia(Long estudianteId, Long sequenceId);

    /** Upsert: crea o reemplaza la fila de progreso (es una proyección recalculable, no un historial). */
    StudentSequenceProgress guardar(StudentSequenceProgress progreso);

    List<StudentSequenceProgress> listarPorSecuencia(Long sequenceId);
}
