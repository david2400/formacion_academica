package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FeedbackQuestionJpaRepository extends JpaRepository<FeedbackQuestionEntity, Long> {

    List<FeedbackQuestionEntity> findByFormIdOrderBySectionIdAscOrdenAsc(Long formId);

    List<FeedbackQuestionEntity> findBySectionIdOrderByOrdenAsc(Long sectionId);

    /**
     * ¿Usa la escala alguna pregunta de un formulario que ya no es editable?
     * (sus niveles ya dan significado a respuestas guardadas).
     */
    @Query("""
            SELECT COUNT(q) > 0 FROM FeedbackQuestionEntity q, FeedbackFormEntity f
            WHERE q.formId = f.id AND q.scaleId = :scaleId
              AND (f.estadoId IS NULL OR f.estadoId NOT IN :editables)
            """)
    boolean escalaEnUsoFueraDe(@Param("scaleId") Long scaleId, @Param("editables") Collection<Long> editables);
}
