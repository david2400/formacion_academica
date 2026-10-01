package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoReglaDependencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Regla de desbloqueo entre dos items de la misma secuencia. Corrección
 * respecto al documento original: el valor umbral se separó en dos campos
 * ({@code ruleValorNumerico} / {@code ruleValorFecha}) en vez de un único
 * DECIMAL para todos los tipos de regla — DATE_AFTER necesita una fecha, no
 * un número, y guardar una fecha dentro de un DECIMAL no es correcto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDependencyRule {

    private Long id;
    private Long itemId;
    private Long dependsOnItemId;
    private TipoReglaDependencia ruleType;

    /** Usado por SCORE_GTE (nota mínima) y ATTEMPTS_GTE (intentos mínimos). */
    private BigDecimal ruleValorNumerico;

    /** Usado por DATE_AFTER. */
    private LocalDateTime ruleValorFecha;

    private LocalDateTime createdAt;
}
