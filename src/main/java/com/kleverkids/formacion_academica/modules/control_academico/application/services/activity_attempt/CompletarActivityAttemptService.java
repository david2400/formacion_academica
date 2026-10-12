package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.CompletarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler.ActivityHandler;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler.ActivityHandlerRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.ResultadoAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.student_progress.RecalcularProgresoEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletarActivityAttemptService implements CompletarActivityAttemptUseCase {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;
    private final ActivityHandlerRegistry activityHandlerRegistry;
    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;
    private final ActivityAttemptEventPublisher activityAttemptEventPublisher;
    private final RecalcularProgresoEstudianteService recalcularProgresoEstudianteService;

    @Override
    public ActivityAttempt completar(Long attemptId) {
        ActivityAttempt attempt = activityAttemptRepositoryPort.obtenerPorId(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        if (attempt.getEstado() != EstadoActivityAttempt.IN_PROGRESS) {
            throw new IllegalStateException("Este intento ya fue finalizado");
        }

        Activity activity = activityRepositoryPort.obtenerPorId(attempt.getActivityId())
                .orElseThrow(() -> new IllegalStateException("La actividad de este intento ya no existe"));
        ActivityTypeDefinition tipo = activityTypeDefinitionRepositoryPort.obtenerPorId(activity.getActivityTypeId())
                .orElseThrow(() -> new IllegalStateException("El tipo de actividad asociado ya no existe"));

        List<ActivityContentItem> contenido = activityRepositoryPort.listarContenido(activity.getId());
        List<ActivityAttemptAnswer> respuestas = activityAttemptRepositoryPort.listarRespuestas(attemptId);

        ActivityHandler handler = activityHandlerRegistry.obtener(tipo.getType());
        if (!handler.puedeCompletarse(contenido, respuestas)) {
            throw new IllegalStateException("Aún faltan respuestas por registrar antes de poder finalizar este intento");
        }

        ResultadoAttemptDto resultado = handler.calcularResultado(activity, contenido, respuestas);

        attempt.setCompletedAt(LocalDateTime.now());
        attempt.setEstado(EstadoActivityAttempt.COMPLETED);
        attempt.setScore(resultado.getScore());

        ActivityAttempt guardado = activityAttemptRepositoryPort.guardar(attempt);
        publicarEventoCompletado(guardado);

        if (guardado.getAssignmentId() != null) {
            learningAssignmentRepositoryPort.obtenerPorId(guardado.getAssignmentId())
                    .ifPresent(assignment -> recalcularProgresoEstudianteService.recalcular(guardado.getEstudianteId(), assignment.getSequenceId()));
        }

        return guardado;
    }

    private void publicarEventoCompletado(ActivityAttempt attempt) {
        attempt.registrarEventoCompletado();
        attempt.getDomainEvents().forEach(activityAttemptEventPublisher::publish);
        attempt.clearDomainEvents();
    }
}
