package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteExamenJpaRepository extends JpaRepository<EstudianteExamenEntity, Long> {

    Optional<EstudianteExamenEntity> findByExamenIdAndEstudianteId(Long examenId, Long estudianteId);

    List<EstudianteExamenEntity> findByExamenIdOrderByAsignadoEnAsc(Long examenId);
}
