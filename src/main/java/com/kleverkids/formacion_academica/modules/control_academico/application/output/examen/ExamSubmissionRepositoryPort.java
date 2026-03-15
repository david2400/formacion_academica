package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import java.util.List;
import java.util.Optional;


import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;

public interface ExamSubmissionRepositoryPort {
    
    ExamSubmission save(ExamSubmission submission);
    
    Optional<ExamSubmission> findById(Long id);
    
    Optional<ExamSubmission> findByExamIdAndStudentId(Long examId, Long studentId);
    
    List<ExamSubmission> findByExamId(Long examId);
}
