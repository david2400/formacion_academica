package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TematicaExamenJpaRepository extends JpaRepository<TematicaExamenEntity, UUID> {

    List<TematicaExamenEntity> findByExamenIdOrderByOrdenAsc(UUID examenId);
}
