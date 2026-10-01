package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_assignment.LearningAssignmentEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class LearningAssignmentSpecifications {

    private LearningAssignmentSpecifications() {
    }

    public static Specification<LearningAssignmentEntity> desdeFiltro(FiltroLearningAssignmentDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            if (filtro.getSequenceId() != null) {
                predicados.add(cb.equal(root.get("sequenceId"), filtro.getSequenceId()));
            }
            if (filtro.getGrupoId() != null) {
                predicados.add(cb.equal(root.get("grupoId"), filtro.getGrupoId()));
            }
            if (filtro.getEstudianteId() != null) {
                predicados.add(cb.equal(root.get("estudianteId"), filtro.getEstudianteId()));
            }
            if (filtro.getEstado() != null) {
                predicados.add(cb.equal(root.get("estado"), filtro.getEstado()));
            }

            return predicados.isEmpty() ? cb.conjunction() : cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
