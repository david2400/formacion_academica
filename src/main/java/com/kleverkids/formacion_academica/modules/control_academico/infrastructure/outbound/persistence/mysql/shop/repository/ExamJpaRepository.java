package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExamJpaRepository extends JpaRepository<ExamEntity, UUID> {
    
    @Query("SELECT e FROM ExamEntity e WHERE e.deleted = false " +
           "AND (:status IS NULL OR e.status = :status) " +
           "AND (:subject IS NULL OR e.subject = :subject) " +
           "AND (:gradeLevel IS NULL OR e.gradeLevel = :gradeLevel) " +
           "AND (:searchText IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<ExamEntity> search(
        @Param("status") String status,
        @Param("subject") String subject,
        @Param("gradeLevel") String gradeLevel,
        @Param("searchText") String searchText,
        Pageable pageable
    );
}
