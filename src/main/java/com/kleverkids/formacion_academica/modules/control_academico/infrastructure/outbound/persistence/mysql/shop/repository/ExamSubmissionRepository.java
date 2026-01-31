package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamSubmission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamSubmissionRepository {
    
    ExamSubmission save(ExamSubmission submission);
    
    Optional<ExamSubmission> findById(UUID id);
    
    Optional<ExamSubmission> findByExamIdAndStudentId(UUID examId, UUID studentId);
    
    List<ExamSubmission> findByExamId(UUID examId);
}
