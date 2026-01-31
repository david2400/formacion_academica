package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaPreguntaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RespuestaPreguntaJpaRepository extends JpaRepository<RespuestaPreguntaEntity, UUID> {

    List<RespuestaPreguntaEntity> findByExamenIdAndEstudianteIdOrderByRegistradaEnAsc(UUID examenId, UUID estudianteId);
}
