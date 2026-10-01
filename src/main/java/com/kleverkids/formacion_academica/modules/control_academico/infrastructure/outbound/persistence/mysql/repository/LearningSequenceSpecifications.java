package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class LearningSequenceSpecifications {

    private LearningSequenceSpecifications() {
    }

    public static Specification<LearningSequenceEntity> desdeFiltro(FiltroLearningSequenceDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            if (filtro.getTexto() != null && !filtro.getTexto().isBlank()) {
                String patron = "%" + filtro.getTexto().trim().toLowerCase() + "%";
                predicados.add(cb.like(cb.lower(root.get("titulo")), patron));
            }

            if (filtro.getEstado() != null) {
                predicados.add(cb.equal(root.get("estado"), filtro.getEstado()));
            }

            return predicados.isEmpty() ? cb.conjunction() : cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
