package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;



public record RespuestaIntentoDto(Long id,
                                  Long intentoId,
                                  Long preguntaId,
                                  String respuesta,
                                  boolean esCorrecta,
                                  Integer puntajeObtenido) {
}
