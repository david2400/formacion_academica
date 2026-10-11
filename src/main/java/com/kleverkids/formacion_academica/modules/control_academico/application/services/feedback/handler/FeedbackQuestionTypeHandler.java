package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;

import java.util.List;

/**
 * Comportamiento de un tipo de pregunta (mismo patrón que ActivityHandler):
 * agregar un tipo = un valor de FeedbackQuestionType + un {@code @Component}
 * con su nombre que implemente esta interfaz. Ni el servicio ni el
 * controlador conocen la lista de tipos.
 *
 * <p>Los mensajes de error se escriben en lenguaje de usuario: llegan tal cual
 * a la pantalla del coordinador o del estudiante.
 */
public interface FeedbackQuestionTypeHandler {

    /** Valida la configuración de la pregunta. Lanza IllegalArgumentException si no es coherente. */
    void validarDefinicion(DefinicionPreguntaFeedback definicion);

    /** ¿El estudiante respondió algo para esta pregunta? (decide la regla de obligatoria). */
    boolean respondida(RespuestaPreguntaFeedbackDto respuesta);

    /**
     * Convierte una respuesta ya marcada como respondida en filas de
     * feedback_answers. Lanza IllegalArgumentException si el valor no es válido.
     */
    List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback definicion, RespuestaPreguntaFeedbackDto respuesta);
}
