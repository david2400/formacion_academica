package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RespuestaCriterioDto(UUID id,
                                   UUID estudianteExamenId,
                                   UUID examenId,
                                   UUID criterioId,
                                   UUID estudianteId,
                                   String respuesta,
                                   BigDecimal puntajeObtenido,
                                   LocalDateTime registradaEn) {
}
