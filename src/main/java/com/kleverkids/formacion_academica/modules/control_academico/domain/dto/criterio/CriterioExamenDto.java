package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio;

import java.math.BigDecimal;

public record CriterioExamenDto(Long id,
                                Long examenId,
                                String nombre,
                                String descripcion,
                                BigDecimal ponderacion,
                                Integer orden,
                                String recomendacionBase) {
}
