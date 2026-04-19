package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;

public record RubricaDto(
        List<CriterioDto> criterios
) {

    public record CriterioDto(
            String nombre,
            String descripcion,
            Integer puntajeMaximo,
            List<NivelDto> niveles
    ) {}

    public record NivelDto(
            String nombre,
            String descripcion,
            Integer puntaje
    ) {}
}