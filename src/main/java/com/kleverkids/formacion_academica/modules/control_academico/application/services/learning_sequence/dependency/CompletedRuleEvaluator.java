package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * El item del que se depende debe tener al menos un intento COMPLETED
 * (cualquier resultado, aprobado o no). Si {@code dependsOnItem} es un GROUP,
 * se exige que TODAS sus actividades hijas directas tengan al menos un
 * intento COMPLETED — interpretación tomada sin especificación explícita del
 * encargo original (ver {@link DependencyRuleEvaluator}).
 */
@Component("COMPLETED")
@RequiredArgsConstructor
public class CompletedRuleEvaluator implements DependencyRuleEvaluator {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public boolean evaluar(LearningSequenceItem dependsOnItem, ActivityDependencyRule rule, Long estudianteId) {
        if (dependsOnItem.getItemType() == TipoItemSecuencia.ACTIVITY) {
            return tieneIntentoCompletado(dependsOnItem.getActivityId(), estudianteId);
        }
        List<LearningSequenceItem> hijos = learningSequenceRepositoryPort.listarItems(dependsOnItem.getSequenceId()).stream()
                .filter(i -> dependsOnItem.getId().equals(i.getParentItemId()))
                .toList();
        if (hijos.isEmpty()) {
            return true;
        }
        return hijos.stream().allMatch(hijo -> tieneIntentoCompletado(hijo.getActivityId(), estudianteId));
    }

    private boolean tieneIntentoCompletado(Long activityId, Long estudianteId) {
        return activityAttemptRepositoryPort.listarPorEstudianteYActividad(estudianteId, activityId).stream()
                .anyMatch(a -> a.getEstado() == EstadoActivityAttempt.COMPLETED);
    }
}
