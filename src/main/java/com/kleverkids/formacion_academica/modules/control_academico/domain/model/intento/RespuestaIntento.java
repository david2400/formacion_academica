package com.kleverkids.formacion_academica.modules.control_academico.domain.model.intento;

import java.util.UUID;

public record RespuestaIntento(UUID id,
                               UUID intentoId,
                               UUID preguntaId,
                               String respuesta,
                               boolean esCorrecta,
                               Integer puntajeObtenido) {
}
