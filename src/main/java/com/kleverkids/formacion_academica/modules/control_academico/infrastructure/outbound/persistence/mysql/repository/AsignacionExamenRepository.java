package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionExamenRepository extends 
    JpaRepository<AsignacionExamenEntity, Long>,
    JpaSpecificationExecutor<AsignacionExamenEntity> {
    
    boolean existsByExamenIdAndClaseIdAndEstado(Long examenId, Long claseId, String estado);
}
