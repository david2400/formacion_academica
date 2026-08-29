package com.kleverkids.formacion_academica.modules.control_academico.application.services.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.BuscarAsignacionesDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AsignacionExamenSpecifications {
    
    public static Specification<AsignacionExamenEntity> withCriteria(BuscarAsignacionesDto criterios) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (criterios.getExamenId() != null) {
                predicates.add(cb.equal(root.get("examenId"), criterios.getExamenId()));
            }
            
            if (criterios.getClaseId() != null) {
                predicates.add(cb.equal(root.get("claseId"), criterios.getClaseId()));
            }
            
            if (criterios.getGrado() != null && !criterios.getGrado().isBlank()) {
                predicates.add(cb.equal(root.get("grado"), criterios.getGrado()));
            }
            
            if (criterios.getGrupo() != null && !criterios.getGrupo().isBlank()) {
                predicates.add(cb.equal(root.get("grupo"), criterios.getGrupo()));
            }
            
            if (criterios.getEstado() != null && !criterios.getEstado().isBlank()) {
                predicates.add(cb.equal(root.get("estado"), criterios.getEstado()));
            }
            
            if (criterios.getFechaDesde() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                    root.get("fechaInicio"), 
                    criterios.getFechaDesde().atStartOfDay()
                ));
            }
            
            if (criterios.getFechaHasta() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                    root.get("fechaFin"), 
                    criterios.getFechaHasta().atTime(23, 59, 59)
                ));
            }
            
            if (criterios.getActivas() != null && criterios.getActivas()) {
                LocalDateTime now = LocalDateTime.now();
                predicates.add(cb.and(
                    cb.lessThanOrEqualTo(root.get("fechaInicio"), now),
                    cb.greaterThanOrEqualTo(root.get("fechaFin"), now),
                    cb.equal(root.get("estado"), "ACTIVA")
                ));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
