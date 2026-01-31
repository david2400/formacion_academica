package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamSubmissionJpaRepository extends JpaRepository<ExamSubmissionEntity, UUID> {
    
    Optional<ExamSubmissionEntity> findByExamIdAndStudentId(UUID examId, UUID studentId);
    
    List<ExamSubmissionEntity> findByExamId(UUID examId);
}
