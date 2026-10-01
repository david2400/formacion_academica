package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Registro poblado automáticamente por Spring: cada
 * {@code @Component("TIPO_REGLA")} que implemente {@link DependencyRuleEvaluator}
 * se agrega solo a este mapa por nombre de bean (igual que
 * {@code ActivityHandlerRegistry}).
 */
@Component
@RequiredArgsConstructor
public class DependencyRuleEvaluatorRegistry {

    private final Map<String, DependencyRuleEvaluator> evaluators;

    public DependencyRuleEvaluator obtener(String ruleType) {
        DependencyRuleEvaluator evaluator = evaluators.get(ruleType);
        if (evaluator == null) {
            throw new IllegalStateException(
                    "No existe un evaluador registrado para el tipo de regla '" + ruleType + "'");
        }
        return evaluator;
    }
}
