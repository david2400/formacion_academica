package com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta;

import java.time.LocalDateTime;
import java.util.UUID;

public record RespuestaPregunta(UUID id,
                                UUID estudianteExamenId,
                                UUID examenId,
                                UUID estudianteId,
                                UUID preguntaId,
                                UUID respuestaBancoId,
                                String respuestaTexto,
                                Boolean esCorrecta,
                                Integer puntajeObtenido,
                                LocalDateTime registradaEn) {
}
