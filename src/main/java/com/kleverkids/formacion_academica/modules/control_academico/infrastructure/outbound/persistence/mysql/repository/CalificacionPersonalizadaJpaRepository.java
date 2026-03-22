package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.CalificacionPersonalizadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CalificacionPersonalizadaJpaRepository extends JpaRepository<CalificacionPersonalizadaEntity, Long> {
}
