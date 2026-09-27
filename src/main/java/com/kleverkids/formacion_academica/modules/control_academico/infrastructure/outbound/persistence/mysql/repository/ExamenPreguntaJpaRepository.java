package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ExamenPreguntaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamenPreguntaJpaRepository extends JpaRepository<ExamenPreguntaEntity, Long> {

    List<ExamenPreguntaEntity> findByExamenIdOrderByOrdenAsc(Long examenId);

    Optional<ExamenPreguntaEntity> findByExamenIdAndPreguntaId(Long examenId, Long preguntaId);

    boolean existsByExamenIdAndPreguntaId(Long examenId, Long preguntaId);
}
