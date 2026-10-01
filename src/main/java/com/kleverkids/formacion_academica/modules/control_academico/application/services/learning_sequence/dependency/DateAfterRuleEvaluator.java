package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** Pura comparación de fecha; no depende del estado del item {@code dependsOnItem}. */
@Component("DATE_AFTER")
public class DateAfterRuleEvaluator implements DependencyRuleEvaluator {

    @Override
    public boolean evaluar(LearningSequenceItem dependsOnItem, ActivityDependencyRule rule, Long estudianteId) {
        if (rule.getRuleValorFecha() == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(rule.getRuleValorFecha());
    }
}
