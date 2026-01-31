package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion;

import java.math.BigDecimal;
import java.util.UUID;

public record ObservacionCriterioDto(UUID id,
                                     UUID examenId,
                                     UUID criterioId,
                                     UUID estudianteId,
                                     BigDecimal puntaje,
                                     String observacion,
                                     String recomendacion) {
}
