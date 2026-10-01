package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * La mejor nota obtenida en el item del que se depende debe ser >=
 * {@code rule.ruleValorNumerico}. Solo tiene un significado claro cuando
 * {@code dependsOnItem} es una ACTIVITY puntual (una "nota" agregada de un
 * GROUP no es un concepto que el encargo original defina) — si se aplica
 * sobre un GROUP, se evalúa como no cumplida y se registra en log, en vez de
 * fallar silenciosamente o lanzar una excepción en medio de un flujo de
 * consulta de desbloqueo.
 */
@Component("SCORE_GTE")
@RequiredArgsConstructor
public class ScoreGteRuleEvaluator implements DependencyRuleEvaluator {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ScoreGteRuleEvaluator.class);

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;

    @Override
    public boolean evaluar(LearningSequenceItem dependsOnItem, ActivityDependencyRule rule, Long estudianteId) {
        if (dependsOnItem.getItemType() != TipoItemSecuencia.ACTIVITY) {
            log.warn("Regla SCORE_GTE definida sobre un item GROUP (id={}): no tiene un criterio de nota agregada definido, se evalúa como no cumplida.", dependsOnItem.getId());
            return false;
        }
        if (rule.getRuleValorNumerico() == null) {
            return false;
        }
        BigDecimal mejorPuntaje = activityAttemptRepositoryPort.listarPorEstudianteYActividad(estudianteId, dependsOnItem.getActivityId()).stream()
                .filter(a -> a.getEstado() == EstadoActivityAttempt.COMPLETED)
                .map(ActivityAttempt::getScore)
                .filter(java.util.Objects::nonNull)
                .max(BigDecimal::compareTo)
                .orElse(null);
        return mejorPuntaje != null && mejorPuntaje.compareTo(rule.getRuleValorNumerico()) >= 0;
    }
}
