package com.kleverkids.formacion_academica.modules.control_academico.domain.model.intento;



public record RespuestaIntento(Long id,
                               Long intentoId,
                               Long preguntaId,
                               String respuesta,
                               boolean esCorrecta,
                               Integer puntajeObtenido) {
}
