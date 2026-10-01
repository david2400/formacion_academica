package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Una respuesta dentro de un intento, una fila por {@code ActivityContentItem}
 * de tipo QUESTION respondido. No existe para items de tipo TEXT (ver
 * {@code ActivityHandler.puedeCompletarse}).
 *
 * <p>Corrección respecto al documento original: el documento proponía una
 * columna {@code respuesta TEXT} única para toda forma de respuesta. La forma
 * real de una respuesta varía genuinamente por tipo de pregunta (ids de
 * opción, booleano, texto libre, numérico, orden, pares de emparejamiento —
 * ver {@code SolicitudValidacionRespuesta}, que ya modela esto), que es
 * exactamente el caso en que el propio proyecto considera apropiado usar JSON
 * acotado (ver arquitectura aprobada §3.2). Se guarda entonces como
 * {@code respuestaJson}, serializando la misma {@code SolicitudValidacionRespuesta}
 * que el estudiante envió.</p>
 *
 * <p>{@code puntajeObtenido} ya viene normalizado a la escala de puntos de la
 * actividad (ver {@code ActivityAttemptService.registrarRespuesta}): el
 * {@code ValidationResult} de {@code ServicioValidacionRespuesta} califica en
 * la escala propia de la Pregunta ({@code puntajeMaximo}), que no es
 * necesariamente la misma escala que {@code ActivityContentItem.puntos}.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAttemptAnswer {

    private Long id;
    private Long attemptId;
    private Long contentItemId;

    /** FK directo a questions.id (mismo módulo). */
    private Long preguntaId;

    private Map<String, Object> respuestaJson;
    private Boolean esCorrecta;
    private BigDecimal puntajeObtenido;

    /** Snapshot ligero (texto + opciones) de lo que el estudiante vio al responder — mejora recomendada en el documento original §2.2, opcional. */
    private Map<String, Object> preguntaSnapshot;

    private LocalDateTime registradaEn;
}
