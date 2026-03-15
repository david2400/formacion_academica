package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion;

import java.math.BigDecimal;


public record ObservacionCriterioDto(Long id,
                                     Long examenId,
                                     Long criterioId,
                                     Long estudianteId,
                                     BigDecimal puntaje,
                                     String observacion,
                                     String recomendacion) {
}
