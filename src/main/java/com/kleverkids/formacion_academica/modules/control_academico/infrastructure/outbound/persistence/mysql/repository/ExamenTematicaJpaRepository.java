package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenTematicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamenTematicaJpaRepository extends JpaRepository<ExamenTematicaEntity, Long> {

    List<ExamenTematicaEntity> findByExamenId(Long examenId);

    Optional<ExamenTematicaEntity> findByExamenIdAndTematicaId(Long examenId, Long tematicaId);

    boolean existsByExamenIdAndTematicaId(Long examenId, Long tematicaId);

    void deleteByExamenIdAndTematicaId(Long examenId, Long tematicaId);
}
