package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CriterioExamen(Long id,
                             Long examenId,
                             String nombre,
                             String descripcion,
                             BigDecimal ponderacion,
                             Integer orden,
                             String recomendacionBase,
                             boolean eliminado,
                             Integer usrCrea,
                             Integer usrMod,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {
}
