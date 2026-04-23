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
            
            if (criterios.examenId() != null) {
                predicates.add(cb.equal(root.get("examenId"), criterios.examenId()));
            }
            
            if (criterios.claseId() != null) {
                predicates.add(cb.equal(root.get("claseId"), criterios.claseId()));
            }
            
            if (criterios.grado() != null && !criterios.grado().isBlank()) {
                predicates.add(cb.equal(root.get("grado"), criterios.grado()));
            }
            
            if (criterios.grupo() != null && !criterios.grupo().isBlank()) {
                predicates.add(cb.equal(root.get("grupo"), criterios.grupo()));
            }
            
            if (criterios.estado() != null && !criterios.estado().isBlank()) {
                predicates.add(cb.equal(root.get("estado"), criterios.estado()));
            }
            
            if (criterios.fechaDesde() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                    root.get("fechaInicio"), 
                    criterios.fechaDesde().atStartOfDay()
                ));
            }
            
            if (criterios.fechaHasta() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                    root.get("fechaFin"), 
                    criterios.fechaHasta().atTime(23, 59, 59)
                ));
            }
            
            if (criterios.activas() != null && criterios.activas()) {
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
