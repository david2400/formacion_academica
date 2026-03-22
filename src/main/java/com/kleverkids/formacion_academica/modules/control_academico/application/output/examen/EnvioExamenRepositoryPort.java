package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.EnvioExamen;

import java.util.List;
import java.util.Optional;

public interface EnvioExamenRepositoryPort {
    
    // Métodos en español
    EnvioExamen guardar(EnvioExamen envio);
    
    // Métodos de compatibilidad (transición)
    @Deprecated
    EnvioExamen save(EnvioExamen submission);
    @Deprecated
    Optional<EnvioExamen> findExamById(Long id);
    @Deprecated
    Optional<EnvioExamen> findById(Long id);
    @Deprecated
    Optional<EnvioExamen> findByExamIdAndStudentId(Long examId, Long studentId);
    @Deprecated
    List<EnvioExamen> findByExamId(Long examId);
}
