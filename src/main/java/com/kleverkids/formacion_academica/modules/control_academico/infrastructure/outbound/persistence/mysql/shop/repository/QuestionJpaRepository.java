package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, UUID> {
    
    @Query("SELECT q FROM QuestionEntity q WHERE q.deleted = false " +
           "AND (:questionType IS NULL OR q.questionType = :questionType) " +
           "AND (:difficulty IS NULL OR q.difficulty = :difficulty) " +
           "AND (:themeId IS NULL OR q.themeId = :themeId) " +
           "AND (:searchText IS NULL OR LOWER(q.questionText) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<QuestionEntity> search(
        @Param("questionType") String questionType,
        @Param("difficulty") String difficulty,
        @Param("themeId") UUID themeId,
        @Param("searchText") String searchText,
        Pageable pageable
    );
    
    @Query("SELECT q FROM QuestionEntity q WHERE q.deleted = :includeDeleted OR q.deleted = false " +
           "AND (:questionType IS NULL OR q.questionType = :questionType) " +
           "AND (:difficulty IS NULL OR q.difficulty = :difficulty) " +
           "AND (:themeId IS NULL OR q.themeId = :themeId) " +
           "AND (:searchText IS NULL OR LOWER(q.questionText) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<QuestionEntity> searchWithDeleted(
        @Param("questionType") String questionType,
        @Param("difficulty") String difficulty,
        @Param("themeId") UUID themeId,
        @Param("searchText") String searchText,
        @Param("includeDeleted") boolean includeDeleted,
        Pageable pageable
    );
    
    List<QuestionEntity> findByThemeIdAndDeletedFalse(UUID themeId);
    
    List<QuestionEntity> findByIdInAndDeletedFalse(List<UUID> ids);
}
