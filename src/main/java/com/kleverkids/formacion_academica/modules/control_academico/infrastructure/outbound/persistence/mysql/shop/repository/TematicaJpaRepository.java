package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TematicaJpaRepository extends JpaRepository<TematicaEntity, Long> {

    List<TematicaEntity> findByExamenIdOrderByOrdenAsc(Long examenId);
}
