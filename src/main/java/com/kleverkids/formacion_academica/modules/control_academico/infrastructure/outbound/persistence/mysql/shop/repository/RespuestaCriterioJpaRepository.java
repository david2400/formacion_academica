package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaCriterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RespuestaCriterioJpaRepository extends JpaRepository<RespuestaCriterioEntity, UUID> {

    List<RespuestaCriterioEntity> findByExamenIdAndEstudianteIdOrderByRegistradaEnAsc(UUID examenId, UUID estudianteId);
}
