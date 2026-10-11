package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Lleva un valor a 0..1 dentro de [min, max] (base de los índices comparables). */
final class Normalizacion {

    private Normalizacion() {
    }

    static BigDecimal normalizar(BigDecimal valor, BigDecimal min, BigDecimal max) {
        if (valor == null || min == null || max == null || max.compareTo(min) <= 0) {
            return null;
        }
        return valor.subtract(min).divide(max.subtract(min), 4, RoundingMode.HALF_UP);
    }

    static boolean esEntero(BigDecimal valor) {
        return valor.stripTrailingZeros().scale() <= 0;
    }
}
