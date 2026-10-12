package com.kleverkids.formacion_academica.modules.control_academico.application.services.student_progress;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.student_progress.StudentSequenceProgressRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.events.learning_assignment.SequenceCompletedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Recalcula la proyección de progreso de un estudiante en una secuencia a
 * partir de {@code ActivityAttempt} + {@code LearningSequenceItem} (ver
 * javadoc de {@code StudentSequenceProgress}: no es fuente de verdad).
 *
 * <p>No es un caso de uso expuesto: lo invocan {@code CompletarActivityAttemptService}
 * justo después de completar un intento y {@code ConsultarProgresoEstudianteService}
 * cuando aún no existe la proyección — dependencia directa Service→Service dentro
 * del mismo módulo, sin la abstracción extra de un puerto que no tendría más de un
 * consumidor real (ver CLAUDE.md #43, anti-overengineering).</p>
 */
@Service
@RequiredArgsConstructor
public class RecalcularProgresoEstudianteService {

    private final StudentSequenceProgressRepositoryPort studentSequenceProgressRepositoryPort;
    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;
    private final LearningAssignmentEventPublisher learningAssignmentEventPublisher;

    public StudentSequenceProgress recalcular(Long estudianteId, Long sequenceId) {
        List<LearningSequenceItem> actividades = learningSequenceRepositoryPort.listarItems(sequenceId).stream()
                .filter(item -> item.getItemType() == TipoItemSecuencia.ACTIVITY)
                .sorted(Comparator.comparing(LearningSequenceItem::getOrden))
                .toList();

        int total = actividades.size();
        int completados = 0;
        Long siguienteItemId = null;

        for (LearningSequenceItem item : actividades) {
            boolean completado = activityAttemptRepositoryPort
                    .listarPorEstudianteYActividad(estudianteId, item.getActivityId()).stream()
                    .anyMatch(intento -> intento.getEstado() == EstadoActivityAttempt.COMPLETED);
            if (completado) {
                completados++;
            } else if (siguienteItemId == null) {
                siguienteItemId = item.getId();
            }
        }

        BigDecimal porcentaje = total == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(completados)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);

        // El id se deja en null: el adapter resuelve el upsert buscando por
        // (estudianteId, sequenceId) y reutilizando el id existente si lo hay
        // (ver StudentSequenceProgressJpaAdapter.guardar) — evita una
        // consulta duplicada aquí.
        StudentSequenceProgress progreso = new StudentSequenceProgress(
                null, estudianteId, sequenceId, completados, total, siguienteItemId, porcentaje, LocalDateTime.now());

        StudentSequenceProgress guardado = studentSequenceProgressRepositoryPort.guardar(progreso);

        if (total > 0 && completados == total) {
            completarAsignacionSiCorresponde(estudianteId, sequenceId);
        }

        return guardado;
    }

    /**
     * Solo cubre asignaciones directas a un estudiante puntual: resolver si un
     * estudiante pertenece a un grupo asignado es responsabilidad de
     * {@code gestion_alumnos}, fuera de alcance de este módulo — decisión de
     * alcance documentada, no un olvido.
     */
    private void completarAsignacionSiCorresponde(Long estudianteId, Long sequenceId) {
        FiltroLearningAssignmentDto filtro = FiltroLearningAssignmentDto.builder()
                .sequenceId(sequenceId)
                .estudianteId(estudianteId)
                .build();

        learningAssignmentRepositoryPort.buscar(filtro).stream()
                .filter(asignacion -> asignacion.getEstado() == EstadoLearningAssignment.ASSIGNED
                        || asignacion.getEstado() == EstadoLearningAssignment.IN_PROGRESS)
                .findFirst()
                .ifPresent(asignacion -> {
                    learningAssignmentRepositoryPort.actualizarEstado(asignacion.getId(), EstadoLearningAssignment.COMPLETED);
                    learningAssignmentEventPublisher.publish(
                            new SequenceCompletedEvent(estudianteId, sequenceId, asignacion.getId()));
                });
    }
}
