package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.math.BigDecimal;


public record ObservacionCriterio(Long id,
                                  Long examenId,
                                  Long criterioId,
                                  Long estudianteId,
                                  BigDecimal puntaje,
                                  String observacion,
                                  String recomendacion) {
}
