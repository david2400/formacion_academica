package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * El item del que se depende debe tener al menos un intento COMPLETED cuyo
 * puntaje sea >= {@code Activity.notaMinima} (si la actividad no define nota
 * mínima, "aprobado" equivale a "completado" — no hay umbral que exigir).
 * Para GROUP, se exige que todas las actividades hijas estén aprobadas.
 */
@Component("PASSED")
@RequiredArgsConstructor
public class PassedRuleEvaluator implements DependencyRuleEvaluator {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public boolean evaluar(LearningSequenceItem dependsOnItem, ActivityDependencyRule rule, Long estudianteId) {
        if (dependsOnItem.getItemType() == TipoItemSecuencia.ACTIVITY) {
            return aprobada(dependsOnItem.getActivityId(), estudianteId);
        }
        List<LearningSequenceItem> hijos = learningSequenceRepositoryPort.listarItems(dependsOnItem.getSequenceId()).stream()
                .filter(i -> dependsOnItem.getId().equals(i.getParentItemId()))
                .toList();
        if (hijos.isEmpty()) {
            return true;
        }
        return hijos.stream().allMatch(hijo -> aprobada(hijo.getActivityId(), estudianteId));
    }

    private boolean aprobada(Long activityId, Long estudianteId) {
        Activity activity = activityRepositoryPort.obtenerPorId(activityId).orElse(null);
        if (activity == null) {
            return false;
        }
        BigDecimal mejorPuntaje = activityAttemptRepositoryPort.listarPorEstudianteYActividad(estudianteId, activityId).stream()
                .filter(a -> a.getEstado() == EstadoActivityAttempt.COMPLETED)
                .map(ActivityAttempt::getScore)
                .filter(java.util.Objects::nonNull)
                .max(BigDecimal::compareTo)
                .orElse(null);
        if (mejorPuntaje == null) {
            return false;
        }
        return activity.getNotaMinima() == null || mejorPuntaje.compareTo(activity.getNotaMinima()) >= 0;
    }
}
