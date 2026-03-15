package com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta;

import java.time.LocalDateTime;


public record RespuestaPregunta(Long id,
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
