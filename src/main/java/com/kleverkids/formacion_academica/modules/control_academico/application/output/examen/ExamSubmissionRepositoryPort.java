package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;

public interface ExamSubmissionRepositoryPort {
    
    ExamSubmission save(ExamSubmission submission);
    
    Optional<ExamSubmission> findById(UUID id);
    
    Optional<ExamSubmission> findByExamIdAndStudentId(UUID examId, UUID studentId);
    
    List<ExamSubmission> findByExamId(UUID examId);
}
