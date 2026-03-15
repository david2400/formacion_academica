package com.kleverkids.formacion_academica.modules.control_academico.domain.model.criterio;

import java.math.BigDecimal;


public record CriterioExamen(Long id,
                             Long examenId,
                             String nombre,
                             String descripcion,
                             BigDecimal ponderacion,
                             Integer orden,
                             String recomendacionBase) {
}
