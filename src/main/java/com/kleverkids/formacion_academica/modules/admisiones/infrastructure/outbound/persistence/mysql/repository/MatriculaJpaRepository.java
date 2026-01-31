package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, UUID> {

    List<MatriculaEntity> findByEstudianteId(UUID estudianteId);
}
