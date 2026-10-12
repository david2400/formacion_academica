package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.IniciarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency.DependencyRuleEvaluatorRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.IniciarActivityAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Inicia un intento de actividad (Etapa 3), con las validaciones de ventana de
 * asignación, máximo de intentos y dependencias de la secuencia.
 */
@Service
@RequiredArgsConstructor
public class IniciarActivityAttemptService implements IniciarActivityAttemptUseCase {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final DependencyRuleEvaluatorRegistry dependencyRuleEvaluatorRegistry;
    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;
    private final ActivityAttemptEventPublisher activityAttemptEventPublisher;

    @Override
    public ActivityAttempt iniciar(IniciarActivityAttemptDto request) {
        Activity activity = activityRepositoryPort.obtenerPorId(request.getActivityId())
                .orElseThrow(() -> new IllegalArgumentException("La actividad indicada no existe"));
        if (activity.getEstado() != EstadoActivity.PUBLISHED) {
            throw new IllegalStateException("Solo se pueden iniciar intentos sobre actividades publicadas");
        }

        LearningAssignment assignment = null;
        if (request.getAssignmentId() != null) {
            assignment = learningAssignmentRepositoryPort.obtenerPorId(request.getAssignmentId())
                    .orElseThrow(() -> new IllegalArgumentException("La asignación indicada no existe"));
            validarVentanaAsignacion(assignment);
            validarDependencias(assignment, activity.getId(), request.getEstudianteId());
        }

        Optional<ActivityAttempt> enProgreso = activityAttemptRepositoryPort
                .obtenerEnProgreso(activity.getId(), request.getAssignmentId(), request.getEstudianteId());
        if (enProgreso.isPresent()) {
            return enProgreso.get();
        }

        int finalizados = activityAttemptRepositoryPort.contarIntentosFinalizados(
                activity.getId(), request.getAssignmentId(), request.getEstudianteId());
        if (finalizados > 0 && !activity.isPermiteReintentos()) {
            throw new IllegalStateException("Esta actividad no permite reintentos y ya tienes un intento registrado");
        }
        if (activity.getMaxIntentos() != null && finalizados >= activity.getMaxIntentos()) {
            throw new IllegalStateException("Ya alcanzaste el número máximo de intentos permitidos para esta actividad");
        }

        ActivityAttempt nuevo = new ActivityAttempt();
        nuevo.setActivityId(activity.getId());
        nuevo.setActivityVersionId(activity.getVersionActualId());
        nuevo.setAssignmentId(request.getAssignmentId());
        nuevo.setEstudianteId(request.getEstudianteId());
        nuevo.setAttemptNumber(finalizados + 1);
        nuevo.setStartedAt(LocalDateTime.now());
        nuevo.setEstado(EstadoActivityAttempt.IN_PROGRESS);

        ActivityAttempt guardado = activityAttemptRepositoryPort.guardar(nuevo);

        if (assignment != null && assignment.getEstado() == EstadoLearningAssignment.ASSIGNED) {
            learningAssignmentRepositoryPort.actualizarEstado(assignment.getId(), EstadoLearningAssignment.IN_PROGRESS);
        }

        publicarEventoInicio(guardado);
        return guardado;
    }

    private void validarVentanaAsignacion(LearningAssignment assignment) {
        LocalDateTime ahora = LocalDateTime.now();
        if (assignment.getAvailableFrom() != null && ahora.isBefore(assignment.getAvailableFrom())) {
            throw new IllegalStateException("Esta actividad todavía no está disponible");
        }
        if (assignment.getDueAt() != null && ahora.isAfter(assignment.getDueAt())) {
            if (assignment.isLockAfterDue()) {
                throw new IllegalStateException("La fecha límite de esta asignación ya pasó");
            }
            if (!assignment.isAllowLateSubmission()) {
                throw new IllegalStateException(
                        "La fecha límite de esta asignación ya pasó y no se permiten entregas tardías");
            }
        }
    }

    /**
     * Busca el item de la secuencia de la asignación que corresponde a esta
     * actividad y evalúa sus reglas de dependencia. Si la actividad no
     * pertenece a la secuencia de la asignación (dato inconsistente, no
     * debería ocurrir en la práctica) no hay nada que validar aquí.
     */
    private void validarDependencias(LearningAssignment assignment, Long activityId, Long estudianteId) {
        LearningSequenceItem item = learningSequenceRepositoryPort.listarItems(assignment.getSequenceId()).stream()
                .filter(i -> i.getItemType() == TipoItemSecuencia.ACTIVITY && activityId.equals(i.getActivityId()))
                .findFirst()
                .orElse(null);
        if (item == null) {
            return;
        }

        for (ActivityDependencyRule regla : learningSequenceRepositoryPort.listarDependencias(item.getId())) {
            LearningSequenceItem dependeDe = learningSequenceRepositoryPort.obtenerItem(regla.getDependsOnItemId())
                    .orElse(null);
            if (dependeDe == null) {
                continue;
            }
            boolean cumplida = dependencyRuleEvaluatorRegistry.obtener(regla.getRuleType().name())
                    .evaluar(dependeDe, regla, estudianteId);
            if (!cumplida) {
                throw new IllegalStateException(
                        "No puedes iniciar esta actividad todavía: falta cumplir un requisito previo en la secuencia");
            }
        }
    }

    private void publicarEventoInicio(ActivityAttempt attempt) {
        attempt.registrarEventoInicio();
        attempt.getDomainEvents().forEach(activityAttemptEventPublisher::publish);
        attempt.clearDomainEvents();
    }
}
