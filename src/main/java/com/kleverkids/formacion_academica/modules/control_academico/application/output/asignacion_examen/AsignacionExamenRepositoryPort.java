package com.kleverkids.formacion_academica.modules.control_academico.application.output.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface AsignacionExamenRepositoryPort {
    AsignacionExamenEntity save(AsignacionExamenEntity entity);
    Optional<AsignacionExamenEntity> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
    Page<AsignacionExamenEntity> findAll(Specification<AsignacionExamenEntity> spec, Pageable pageable);
    boolean existsByExamenIdAndClaseIdAndEstado(Long examenId, Long claseId, String estado);
}
