package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EstudianteAcudienteJpaRepository extends JpaRepository<EstudianteAcudienteEntity, UUID> {

    List<EstudianteAcudienteEntity> findByEstudianteId(UUID estudianteId);

    List<EstudianteAcudienteEntity> findByAcudienteId(UUID acudienteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrue(UUID estudianteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(UUID estudianteId, UUID relacionId);
}
