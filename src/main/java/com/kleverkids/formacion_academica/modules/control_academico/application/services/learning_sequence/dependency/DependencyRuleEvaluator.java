package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;

/**
 * Punto de extensión para evaluar un tipo de {@code ActivityDependencyRule}
 * (ver arquitectura aprobada §2.5): agregar un tipo de regla nuevo (ej.
 * TIME_SPENT_GTE) es una clase nueva + un bean con nombre igual al enum, sin
 * tocar el núcleo — mismo patrón Map-de-Spring que {@code ActivityHandler}.
 *
 * <p>Nota de alcance: cuando {@code dependsOnItem} es de tipo GROUP, no todos
 * los tipos de regla tienen un significado agregable sin ambigüedad (p. ej.
 * "la nota del grupo" no es un concepto que exista hoy). Las implementaciones
 * de SCORE_GTE/ATTEMPTS_GTE documentan explícitamente su interpretación para
 * ese caso; se marca como una decisión tomada sin especificación explícita
 * del encargo original, pendiente de validación con el usuario si el
 * comportamiento real esperado difiere.
 */
public interface DependencyRuleEvaluator {

    /**
     * @return true si la regla ya se cumple para el estudiante dado (es
     * decir, el item que depende de {@code dependsOnItem} queda desbloqueado
     * respecto a esta regla puntual).
     */
    boolean evaluar(LearningSequenceItem dependsOnItem, ActivityDependencyRule rule, Long estudianteId);
}
