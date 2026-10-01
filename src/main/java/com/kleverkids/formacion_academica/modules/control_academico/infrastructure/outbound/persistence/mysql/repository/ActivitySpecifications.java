package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/** Predicados dinámicos para la búsqueda de actividades. */
public final class ActivitySpecifications {

    private ActivitySpecifications() {
    }

    public static Specification<ActivityEntity> desdeFiltro(FiltroActivityDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            if (filtro.getTexto() != null && !filtro.getTexto().isBlank()) {
                String patron = "%" + filtro.getTexto().trim().toLowerCase() + "%";
                predicados.add(cb.like(cb.lower(root.get("titulo")), patron));
            }

            if (filtro.getActivityTypeId() != null) {
                predicados.add(cb.equal(root.get("activityTypeId"), filtro.getActivityTypeId()));
            }

            if (filtro.getEstado() != null) {
                predicados.add(cb.equal(root.get("estado"), filtro.getEstado()));
            }

            return predicados.isEmpty() ? cb.conjunction() : cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
