package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import java.util.List;
import java.util.Optional;


import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamResult;

public interface ExamResultRepositoryPort {
    
    ExamResult save(ExamResult result);
    
    Optional<ExamResult> findById(Long id);
    
    Optional<ExamResult> findByExamIdAndStudentId(Long examId, Long studentId);
    
    List<ExamResult> findByExamId(Long examId);
}
