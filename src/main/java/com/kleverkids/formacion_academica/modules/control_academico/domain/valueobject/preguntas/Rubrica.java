package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.List;
import java.util.Objects;

public final class Rubrica extends ValueObject {

    private final List<CriterioRubrica> criterios;

    private Rubrica(List<CriterioRubrica> criterios) {
        this.criterios = criterios != null ? List.copyOf(criterios) : List.of();
    }

    public static Rubrica crear(List<CriterioRubrica> criterios) {
        return new Rubrica(criterios);
    }

    public List<CriterioRubrica> obtenerCriterios() {
        return criterios;
    }

    public int obtenerPuntajeMaximoTotal() {
        return criterios.stream()
                .mapToInt(CriterioRubrica::puntajeMaximo)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rubrica rubrica = (Rubrica) o;
        return Objects.equals(criterios, rubrica.criterios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criterios);
    }

    public record CriterioRubrica(
            String nombre,
            String descripcion,
            int puntajeMaximo,
            List<NivelRubrica> niveles
    ) {}

    public record NivelRubrica(
            String nombre,
            String descripcion,
            int puntaje
    ) {}
}