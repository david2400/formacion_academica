package com.kleverkids.formacion_academica.modules.control_academico.domain.model.criterio;

import java.math.BigDecimal;
import java.util.UUID;

public record CriterioExamen(UUID id,
                             UUID examenId,
                             String nombre,
                             String descripcion,
                             BigDecimal ponderacion,
                             Integer orden,
                             String recomendacionBase) {
}
