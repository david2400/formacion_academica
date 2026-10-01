package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.CompletarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.ConsultarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.IniciarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.ListarActivityAttemptsUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.RegistrarRespuestaAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaLookupPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler.ActivityHandler;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler.ActivityHandlerRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence.dependency.DependencyRuleEvaluatorRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.ServicioValidacionRespuesta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.IniciarActivityAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.RegistrarRespuestaAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.ResultadoAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ValidationResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Núcleo de negocio de Etapa 3: iniciar/responder/completar un intento de
 * actividad, incluyendo las validaciones de ventana de asignación, máximo de
 * intentos y dependencias de la secuencia, y la conexión con
 * {@code ServicioValidacionRespuesta} (que hasta ahora no tenía ningún
 * llamador real en el proyecto).
 */
@RequiredArgsConstructor
@Service
public class ActivityAttemptService implements IniciarActivityAttemptUseCase,
        RegistrarRespuestaAttemptUseCase,
        CompletarActivityAttemptUseCase,
        ConsultarActivityAttemptUseCase,
        ListarActivityAttemptsUseCase {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;
    private final ActivityHandlerRegistry activityHandlerRegistry;
    private final DependencyRuleEvaluatorRegistry dependencyRuleEvaluatorRegistry;
    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;
    private final PreguntaLookupPort preguntaLookupPort;
    private final ServicioValidacionRespuesta servicioValidacionRespuesta;
    private final ActivityAttemptEventPublisher activityAttemptEventPublisher;
    private final StudentProgressService studentProgressService;
    private final ObjectMapper objectMapper;

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

    @Override
    public ActivityAttemptAnswer registrarRespuesta(RegistrarRespuestaAttemptDto request) {
        ActivityAttempt attempt = activityAttemptRepositoryPort.obtenerPorId(request.getAttemptId())
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        if (attempt.getEstado() != EstadoActivityAttempt.IN_PROGRESS) {
            throw new IllegalStateException("No se pueden registrar respuestas sobre un intento que ya finalizó");
        }

        ActivityContentItem contentItem = activityRepositoryPort.obtenerContenidoPorId(request.getContentItemId())
                .orElseThrow(() -> new IllegalArgumentException("El contenido indicado no existe"));
        if (!contentItem.getActivityId().equals(attempt.getActivityId())) {
            throw new IllegalArgumentException("El contenido indicado no pertenece a la actividad de este intento");
        }
        if (contentItem.getContentKind() != ContentKind.QUESTION) {
            throw new IllegalStateException("Solo se pueden registrar respuestas sobre bloques de tipo pregunta");
        }

        Pregunta pregunta = preguntaLookupPort.obtenerPorId(contentItem.getPreguntaId())
                .orElseThrow(() -> new IllegalStateException("La pregunta asociada a este contenido ya no existe"));

        ValidationResult resultado = servicioValidacionRespuesta.validate(pregunta, request.getRespuesta());
        BigDecimal puntajeObtenido = normalizarPuntaje(resultado, contentItem.getPuntos());

        ActivityAttemptAnswer respuesta = activityAttemptRepositoryPort
                .obtenerRespuesta(attempt.getId(), contentItem.getId())
                .orElseGet(ActivityAttemptAnswer::new);
        respuesta.setAttemptId(attempt.getId());
        respuesta.setContentItemId(contentItem.getId());
        respuesta.setPreguntaId(pregunta.getId());
        respuesta.setRespuestaJson(objectMapper.convertValue(request.getRespuesta(), new TypeReference<Map<String, Object>>() {
        }));
        respuesta.setEsCorrecta(resultado.isCorrect());
        respuesta.setPuntajeObtenido(puntajeObtenido);
        respuesta.setPreguntaSnapshot(Map.of(
                "tipoPregunta", pregunta.getTipoPregunta().name(),
                "textoPregunta", pregunta.getTextoPregunta()));
        respuesta.setRegistradaEn(LocalDateTime.now());

        return activityAttemptRepositoryPort.guardarRespuesta(respuesta);
    }

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
                    .ifPresent(assignment -> studentProgressService.recalcular(guardado.getEstudianteId(), assignment.getSequenceId()));
        }

        return guardado;
    }

    @Override
    public ActivityAttempt consultarPorId(Long id) {
        return activityAttemptRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
    }

    @Override
    public List<ActivityAttemptAnswer> listarRespuestas(Long attemptId) {
        return activityAttemptRepositoryPort.listarRespuestas(attemptId);
    }

    @Override
    public List<ActivityAttempt> listarPorEstudianteYActividad(Long estudianteId, Long activityId) {
        return activityAttemptRepositoryPort.listarPorEstudianteYActividad(estudianteId, activityId);
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

    /**
     * {@code ValidationResult} califica en la escala propia de la Pregunta
     * ({@code maxScore}), que no es necesariamente la escala de puntos de la
     * Activity ({@code contentItem.getPuntos()}) — se normaliza aquí antes de
     * guardar (ver javadoc de {@code ActivityAttemptAnswer}).
     */
    private BigDecimal normalizarPuntaje(ValidationResult resultado, BigDecimal puntosActividad) {
        if (puntosActividad == null || resultado.getMaxScore() == null
                || resultado.getMaxScore().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return resultado.getScore()
                .divide(resultado.getMaxScore(), 10, RoundingMode.HALF_UP)
                .multiply(puntosActividad)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void publicarEventoInicio(ActivityAttempt attempt) {
        attempt.registrarEventoInicio();
        attempt.getDomainEvents().forEach(activityAttemptEventPublisher::publish);
        attempt.clearDomainEvents();
    }

    private void publicarEventoCompletado(ActivityAttempt attempt) {
        attempt.registrarEventoCompletado();
        attempt.getDomainEvents().forEach(activityAttemptEventPublisher::publish);
        attempt.clearDomainEvents();
    }
}
