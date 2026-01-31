package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AcudienteJpaRepository extends JpaRepository<AcudienteEntity, UUID> {

    List<AcudienteEntity> findByEstudianteId(UUID estudianteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrue(UUID estudianteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(UUID estudianteId, UUID id);
}
