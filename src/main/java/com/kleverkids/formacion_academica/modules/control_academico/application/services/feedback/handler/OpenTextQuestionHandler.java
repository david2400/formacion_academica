package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Comentario abierto. */
@Component("OPEN_TEXT")
public class OpenTextQuestionHandler implements FeedbackQuestionTypeHandler {

    static final int MAX_LONGITUD = 4000;

    @Override
    public void validarDefinicion(DefinicionPreguntaFeedback d) {
        // Sin configuración adicional.
    }

    @Override
    public boolean respondida(RespuestaPreguntaFeedbackDto r) {
        return r != null && r.getTexto() != null && !r.getTexto().isBlank();
    }

    @Override
    public List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback d, RespuestaPreguntaFeedbackDto r) {
        String texto = r.getTexto().trim();
        if (texto.length() > MAX_LONGITUD) {
            throw new IllegalArgumentException("El comentario no puede superar " + MAX_LONGITUD + " caracteres.");
        }
        return List.of(FeedbackAnswer.builder()
                .questionId(d.pregunta().getId())
                .texto(texto)
                .build());
    }
}
