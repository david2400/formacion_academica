package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Escala numérica entera (ej. 0..10), con etiquetas opcionales en los extremos. */
@Component("NUMERIC_SCALE")
public class NumericScaleQuestionHandler implements FeedbackQuestionTypeHandler {

    private static final int MAX_PUNTOS = 100;

    @Override
    public void validarDefinicion(DefinicionPreguntaFeedback d) {
        Integer min = d.pregunta().getEscalaMin();
        Integer max = d.pregunta().getEscalaMax();
        if (min == null || max == null) {
            throw new IllegalArgumentException("Indica el valor mínimo y máximo de la escala.");
        }
        if (max <= min) {
            throw new IllegalArgumentException("El valor máximo de la escala debe ser mayor que el mínimo.");
        }
        if (max - min > MAX_PUNTOS) {
            throw new IllegalArgumentException("La escala no puede tener más de " + MAX_PUNTOS + " puntos.");
        }
    }

    @Override
    public boolean respondida(RespuestaPreguntaFeedbackDto r) {
        return r != null && r.getValorNumerico() != null;
    }

    @Override
    public List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback d, RespuestaPreguntaFeedbackDto r) {
        BigDecimal valor = r.getValorNumerico();
        BigDecimal min = BigDecimal.valueOf(d.pregunta().getEscalaMin());
        BigDecimal max = BigDecimal.valueOf(d.pregunta().getEscalaMax());
        if (!Normalizacion.esEntero(valor) || valor.compareTo(min) < 0 || valor.compareTo(max) > 0) {
            throw new IllegalArgumentException("Elige un valor entre " + min + " y " + max + ".");
        }
        return List.of(FeedbackAnswer.builder()
                .questionId(d.pregunta().getId())
                .valorNumerico(valor)
                .valorNormalizado(Normalizacion.normalizar(valor, min, max))
                .build());
    }
}
