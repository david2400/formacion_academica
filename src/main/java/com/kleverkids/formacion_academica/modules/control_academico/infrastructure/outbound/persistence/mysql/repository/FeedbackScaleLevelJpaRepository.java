package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackScaleLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FeedbackScaleLevelJpaRepository extends JpaRepository<FeedbackScaleLevelEntity, Long> {

    List<FeedbackScaleLevelEntity> findByScaleIdInOrderByScaleIdAscOrdenAsc(Collection<Long> scaleIds);

    /**
     * Borrado FÍSICO a propósito: con @SoftDelete la fila seguiría ocupando
     * UNIQUE(scale_id, valor) y no se podría volver a crear el mismo valor. Las
     * respuestas guardan el valor, no el id del nivel, así que nada queda huérfano.
     */
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "DELETE FROM feedback_scale_levels WHERE scale_id = :scaleId", nativeQuery = true)
    void borrarFisicamentePorEscala(@Param("scaleId") Long scaleId);
}
