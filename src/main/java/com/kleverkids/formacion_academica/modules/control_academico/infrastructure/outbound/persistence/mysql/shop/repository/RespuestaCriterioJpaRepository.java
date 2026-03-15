package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaCriterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RespuestaCriterioJpaRepository extends JpaRepository<RespuestaCriterioEntity, Long> {

    List<RespuestaCriterioEntity> findByExamenIdAndEstudianteIdOrderByRegistradaEnAsc(Long examenId, Long estudianteId);
}
