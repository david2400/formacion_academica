package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import java.util.List;
import java.util.Optional;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ResultadoExamen;

public interface ResultadoExamenRepositoryPort {
    
    ResultadoExamen save(ResultadoExamen result);
    
    Optional<ResultadoExamen> findById(Long id);
    
    Optional<ResultadoExamen> findByExamIdAndStudentId(Long examId, Long studentId);
    
    List<ResultadoExamen> findByExamId(Long examId);
}
