package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CriterioExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CriterioExamenJpaRepository extends JpaRepository<CriterioExamenEntity, UUID> {

    List<CriterioExamenEntity> findByExamenIdOrderByOrdenAsc(UUID examenId);
}
