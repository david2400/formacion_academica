package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaPreguntaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaPreguntaJpaRepository extends JpaRepository<RespuestaPreguntaEntity, Long> {

    List<RespuestaPreguntaEntity> findByExamenIdAndEstudianteIdOrderByRegistradaEnAsc(Long examenId, Long estudianteId);
}
