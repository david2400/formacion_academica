package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ObservacionCriterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObservacionCriterioJpaRepository extends JpaRepository<ObservacionCriterioEntity, UUID> {

    List<ObservacionCriterioEntity> findByExamenIdAndEstudianteId(UUID examenId, UUID estudianteId);
}
