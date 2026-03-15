package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcudienteJpaRepository extends JpaRepository<AcudienteEntity, Long> {

    List<AcudienteEntity> findByEstudianteId(Long estudianteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrue(Long estudianteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(Long estudianteId, Long id);
}
