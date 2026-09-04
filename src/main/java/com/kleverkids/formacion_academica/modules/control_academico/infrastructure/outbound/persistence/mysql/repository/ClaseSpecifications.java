package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.FiltroClasesDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ClaseEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/** Predicados dinámicos para la búsqueda de clases. */
public final class ClaseSpecifications {

    private ClaseSpecifications() {
    }

    public static Specification<ClaseEntity> desdeFiltro(FiltroClasesDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicados = new ArrayList<>();

            if (filtro.getTexto() != null && !filtro.getTexto().isBlank()) {
                String patron = "%" + filtro.getTexto().trim().toLowerCase() + "%";
                predicados.add(cb.or(
                        cb.like(cb.lower(root.get("nombre")), patron),
                        cb.like(cb.lower(root.get("codigo")), patron)));
            }

            if (filtro.getEstado() != null) {
                predicados.add(cb.equal(root.get("estado"), filtro.getEstado()));
            }

            // fecha_inicio es DATETIME: se compara contra el inicio y el fin del día
            // para que el rango sea inclusivo en ambos extremos.
            if (filtro.getDesde() != null) {
                predicados.add(cb.greaterThanOrEqualTo(
                        root.get("fechaInicio"), filtro.getDesde().atStartOfDay()));
            }

            if (filtro.getHasta() != null) {
                predicados.add(cb.lessThanOrEqualTo(
                        root.get("fechaInicio"), filtro.getHasta().atTime(LocalTime.MAX)));
            }

            return predicados.isEmpty() ? cb.conjunction() : cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
