package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionJpaRepository extends JpaRepository<InscripcionEntity, Long> {

    List<InscripcionEntity> findByPeriodoAcademico(String periodoAcademico);

    List<InscripcionEntity> findByEstado(String estado);
}
