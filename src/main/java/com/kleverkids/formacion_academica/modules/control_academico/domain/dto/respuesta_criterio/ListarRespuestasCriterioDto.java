package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;



public record ListarRespuestasCriterioDto(Long examenId,
                                          Long estudianteId) {

    public ListarRespuestasCriterioDto {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
    }
}
