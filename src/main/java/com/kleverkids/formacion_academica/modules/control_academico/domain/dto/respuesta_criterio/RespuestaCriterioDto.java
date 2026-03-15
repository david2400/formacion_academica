package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record RespuestaCriterioDto(Long id,
                                   Long estudianteExamenId,
                                   Long examenId,
                                   Long criterioId,
                                   Long estudianteId,
                                   String respuesta,
                                   BigDecimal puntajeObtenido,
                                   LocalDateTime registradaEn) {
}
