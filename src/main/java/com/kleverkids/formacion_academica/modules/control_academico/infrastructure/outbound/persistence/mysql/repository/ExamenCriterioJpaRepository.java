package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ExamenCriterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamenCriterioJpaRepository extends JpaRepository<ExamenCriterioEntity, Long> {

    List<ExamenCriterioEntity> findByExamenIdOrderByOrdenAsc(Long examenId);

    Optional<ExamenCriterioEntity> findByExamenIdAndCriterioEvaluadoId(Long examenId, Long criterioEvaluadoId);

    boolean existsByExamenIdAndCriterioEvaluadoId(Long examenId, Long criterioEvaluadoId);
}
