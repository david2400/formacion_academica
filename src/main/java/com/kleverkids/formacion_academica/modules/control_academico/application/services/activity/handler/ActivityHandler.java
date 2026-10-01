package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.ResultadoAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;

import java.util.List;

/**
 * Punto de extensión de la arquitectura de tipos de actividad (ver
 * arquitectura aprobada, sección "ActivityTypeDefinition" / "ActivityHandler
 * registry"). Agregar un tipo de actividad nuevo = una clase que implemente
 * esta interfaz + una fila en {@code activity_type_definition}; cero cambios
 * al núcleo (Service/Controller no conocen la lista de tipos).
 *
 * <p>Etapa 3 agrega los métodos de ciclo de vida de un intento
 * ({@code puedeCompletarse}/{@code calcularResultado}), ahora que existe
 * {@code ActivityAttempt} — en Etapa 1 solo existía {@code validarPublicacion}
 * porque agregarlos antes habría dejado métodos sin ningún llamador real.</p>
 */
public interface ActivityHandler {

    /**
     * Valida que el contenido actual de la actividad sea suficiente y válido
     * para su tipo. Debe lanzar {@link IllegalStateException} (con un mensaje
     * en lenguaje de usuario, no técnico) cuando no se cumpla.
     */
    void validarPublicacion(List<ActivityContentItem> contenido);

    /**
     * @return true si, dadas las respuestas ya registradas en el intento, el
     * tipo de actividad considera que puede marcarse como completado. La
     * calificación en sí (correcta/incorrecta) ya la resolvió
     * {@code ServicioValidacionRespuesta} al registrar cada respuesta; esto
     * solo decide "¿falta algo por responder para este tipo de actividad?".
     */
    boolean puedeCompletarse(List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas);

    /**
     * Calcula el resultado agregado del intento (puntaje en la escala de
     * puntos de la Activity + si se considera aprobado) a partir de las
     * respuestas ya calificadas individualmente.
     */
    ResultadoAttemptDto calcularResultado(Activity activity, List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas);
}
