package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import java.util.UUID;

public record RespuestaIntentoDto(UUID id,
                                  UUID intentoId,
                                  UUID preguntaId,
                                  String respuesta,
                                  boolean esCorrecta,
                                  Integer puntajeObtenido) {
}
