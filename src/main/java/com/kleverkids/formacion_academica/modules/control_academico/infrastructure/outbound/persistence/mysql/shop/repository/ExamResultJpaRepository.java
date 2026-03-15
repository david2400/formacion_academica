package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExamResultJpaRepository extends JpaRepository<ExamResultEntity, Long> {
    
    Optional<ExamResultEntity> findByExamIdAndStudentId(Long examId, Long studentId);
    
    List<ExamResultEntity> findByExamId(Long examId);
}
