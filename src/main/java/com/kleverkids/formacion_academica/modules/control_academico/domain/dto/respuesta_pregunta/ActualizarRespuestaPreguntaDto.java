package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta;

import java.util.UUID;

public record ActualizarRespuestaPreguntaDto(UUID id,
                                             String respuestaTexto,
                                             UUID respuestaBancoId,
                                             Boolean esCorrecta,
                                             Integer puntajeObtenido) {

    public ActualizarRespuestaPreguntaDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador de la respuesta es obligatorio");
        }
        boolean sinCambios = (respuestaTexto == null || respuestaTexto.isBlank())
                && respuestaBancoId == null
                && esCorrecta == null
                && puntajeObtenido == null;
        if (sinCambios) {
            throw new IllegalArgumentException("Debe enviar al menos un cambio");
        }
        if (puntajeObtenido != null && puntajeObtenido < 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}
