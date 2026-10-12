package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_attempt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.RegistrarRespuestaAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaLookupPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.ServicioValidacionRespuesta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.RegistrarRespuestaAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ValidationResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Registra una respuesta dentro de un intento de actividad, conectando con
 * {@code ServicioValidacionRespuesta} para validarla y calificarla.
 */
@Service
@RequiredArgsConstructor
public class RegistrarRespuestaAttemptService implements RegistrarRespuestaAttemptUseCase {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final PreguntaLookupPort preguntaLookupPort;
    private final ServicioValidacionRespuesta servicioValidacionRespuesta;
    private final ObjectMapper objectMapper;

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
}
