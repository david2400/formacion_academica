package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackFormEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackFormJpaRepository extends JpaRepository<FeedbackFormEntity, Long> {

    @Query("""
            SELECT f FROM FeedbackFormEntity f
            WHERE (:estadoId IS NULL OR f.estadoId = :estadoId)
              AND (:texto IS NULL OR LOWER(f.titulo) LIKE LOWER(CONCAT('%', :texto, '%')))
            """)
    Page<FeedbackFormEntity> buscar(@Param("texto") String texto, @Param("estadoId") Long estadoId, Pageable pageable);

    @Query("SELECT COALESCE(MAX(f.version), 0) FROM FeedbackFormEntity f WHERE f.id = :raiz OR f.origenFormId = :raiz")
    int maxVersionDeFamilia(@Param("raiz") Long raiz);
}
