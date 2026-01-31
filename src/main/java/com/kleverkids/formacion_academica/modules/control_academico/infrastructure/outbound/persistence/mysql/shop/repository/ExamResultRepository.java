package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamResultRepository {
    
    ExamResult save(ExamResult result);
    
    Optional<ExamResult> findById(UUID id);
    
    Optional<ExamResult> findByExamIdAndStudentId(UUID examId, UUID studentId);
    
    List<ExamResult> findByExamId(UUID examId);
}
