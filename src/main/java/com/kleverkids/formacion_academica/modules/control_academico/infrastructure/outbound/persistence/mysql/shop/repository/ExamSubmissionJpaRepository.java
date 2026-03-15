package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExamSubmissionJpaRepository extends JpaRepository<ExamSubmissionEntity, Long> {
    
    Optional<ExamSubmissionEntity> findByExamIdAndStudentId(Long examId, Long studentId);
    
    List<ExamSubmissionEntity> findByExamId(Long examId);
}
