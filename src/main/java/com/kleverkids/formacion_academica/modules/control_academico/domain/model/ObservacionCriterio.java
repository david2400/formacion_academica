package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ObservacionCriterio(Long id,
                                  Long examenId,
                                  Long criterioExamenId,
                                  Long estudianteId,
                                  String observacion,
                                  String recomendacion,
                                  boolean activo,
                                  Integer usrCrea,
                                  Integer usrMod,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt) {
}
