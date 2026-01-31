package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.AsistenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AsistenciaJpaRepository extends JpaRepository<AsistenciaEntity, UUID> {

    List<AsistenciaEntity> findByEstudianteId(UUID estudianteId);

    List<AsistenciaEntity> findByClaseId(UUID claseId);

    List<AsistenciaEntity> findByEstudianteIdAndClaseId(UUID estudianteId, UUID claseId);

    List<AsistenciaEntity> findByClaseIdAndFechaRegistroBetween(UUID claseId, LocalDateTime desde, LocalDateTime hasta);
}
