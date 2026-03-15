package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, Long> {

    List<MatriculaEntity> findByEstudianteId(Long estudianteId);
}
