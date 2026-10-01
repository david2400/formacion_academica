package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * El número de intentos (cualquier estado, es "cuántas veces lo intentó") del
 * item del que se depende debe ser >= {@code rule.ruleValorNumerico}. Mismo
 * alcance que SCORE_GTE: solo definido para ACTIVITY puntual.
 */
@Component("ATTEMPTS_GTE")
@RequiredArgsConstructor
public class AttemptsGteRuleEvaluator implements DependencyRuleEvaluator {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AttemptsGteRuleEvaluator.class);

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;

    @Override
    public boolean evaluar(LearningSequenceItem dependsOnItem, ActivityDependencyRule rule, Long estudianteId) {
        if (dependsOnItem.getItemType() != TipoItemSecuencia.ACTIVITY) {
            log.warn("Regla ATTEMPTS_GTE definida sobre un item GROUP (id={}): se evalúa como no cumplida.", dependsOnItem.getId());
            return false;
        }
        if (rule.getRuleValorNumerico() == null) {
            return false;
        }
        int intentos = activityAttemptRepositoryPort.listarPorEstudianteYActividad(estudianteId, dependsOnItem.getActivityId()).size();
        return BigDecimal.valueOf(intentos).compareTo(rule.getRuleValorNumerico()) >= 0;
    }
}
