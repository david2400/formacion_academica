package com.kleverkids.formacion_academica.modules.control_academico.domain.model.observacion;

import java.math.BigDecimal;
import java.util.UUID;

public record ObservacionCriterio(UUID id,
                                  UUID examenId,
                                  UUID criterioId,
                                  UUID estudianteId,
                                  BigDecimal puntaje,
                                  String observacion,
                                  String recomendacion) {
}
