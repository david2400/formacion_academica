package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteAcudienteJpaRepository extends JpaRepository<EstudianteAcudienteEntity, Long> {

    List<EstudianteAcudienteEntity> findByEstudianteId(Long estudianteId);

    List<EstudianteAcudienteEntity> findByAcudienteId(Long acudienteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrue(Long estudianteId);

    boolean existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(Long estudianteId, Long relacionId);
}
