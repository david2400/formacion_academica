package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.math.BigDecimal;

public record ReglaCalificacion(String criterio,
                                BigDecimal ponderacion,
                                BigDecimal puntajeMaximo) {
}
