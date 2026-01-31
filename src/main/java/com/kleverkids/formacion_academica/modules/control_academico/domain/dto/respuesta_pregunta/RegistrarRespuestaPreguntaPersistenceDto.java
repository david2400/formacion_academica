package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta;

import java.util.UUID;

public record RegistrarRespuestaPreguntaPersistenceDto(UUID estudianteExamenId,
                                                       UUID examenId,
                                                       UUID estudianteId,
                                                       UUID preguntaId,
                                                       UUID respuestaBancoId,
                                                       String respuestaTexto,
                                                       Boolean esCorrecta,
                                                       Integer puntajeObtenido) {

    public RegistrarRespuestaPreguntaPersistenceDto {
        if (estudianteExamenId == null) {
            throw new IllegalArgumentException("La relación estudiante-examen es obligatoria");
        }
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (preguntaId == null) {
            throw new IllegalArgumentException("La pregunta es obligatoria");
        }
        if ((respuestaTexto == null || respuestaTexto.isBlank()) && respuestaBancoId == null) {
            throw new IllegalArgumentException("Debe proveer una respuesta en texto u opción del banco");
        }
        if (puntajeObtenido != null && puntajeObtenido < 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}
