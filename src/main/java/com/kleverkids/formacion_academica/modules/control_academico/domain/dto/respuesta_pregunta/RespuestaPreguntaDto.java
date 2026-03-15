package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta;

import java.time.LocalDateTime;

public record RespuestaPreguntaDto(Long id,
                                   Long estudianteExamenId,
                                   Long examenId,
                                   Long estudianteId,
                                   Long preguntaId,
                                   Long respuestaBancoId,
                                   String respuestaTexto,
                                   Boolean esCorrecta,
                                   Integer puntajeObtenido,
                                   LocalDateTime registradaEn) {
}
