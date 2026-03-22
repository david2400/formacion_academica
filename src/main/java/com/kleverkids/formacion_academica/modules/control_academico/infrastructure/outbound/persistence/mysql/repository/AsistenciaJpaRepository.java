package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.AsistenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface AsistenciaJpaRepository extends JpaRepository<AsistenciaEntity, Long> {

    List<AsistenciaEntity> findByEstudianteId(Long estudianteId);

    List<AsistenciaEntity> findByClaseId(Long claseId);

    List<AsistenciaEntity> findByEstudianteIdAndClaseId(Long estudianteId, Long claseId);

    List<AsistenciaEntity> findByClaseIdAndFechaRegistroBetween(Long claseId, LocalDateTime desde, LocalDateTime hasta);
}
