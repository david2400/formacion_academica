package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteExamenJpaRepository extends JpaRepository<EstudianteExamenEntity, UUID> {

    Optional<EstudianteExamenEntity> findByExamenIdAndEstudianteId(UUID examenId, UUID estudianteId);

    List<EstudianteExamenEntity> findByExamenIdOrderByAsignadoEnAsc(UUID examenId);
}
