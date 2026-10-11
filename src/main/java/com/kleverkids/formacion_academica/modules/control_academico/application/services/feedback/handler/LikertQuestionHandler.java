package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Escala Likert: el valor debe ser uno de los niveles de la escala reutilizable asociada. */
@Component("LIKERT")
public class LikertQuestionHandler implements FeedbackQuestionTypeHandler {

    @Override
    public void validarDefinicion(DefinicionPreguntaFeedback d) {
        if (d.pregunta().getScaleId() == null) {
            throw new IllegalArgumentException("Selecciona la escala que usará la pregunta.");
        }
        if (d.niveles().size() < 2) {
            throw new IllegalArgumentException("La escala seleccionada necesita al menos 2 niveles.");
        }
    }

    @Override
    public boolean respondida(RespuestaPreguntaFeedbackDto r) {
        return r != null && r.getValorNumerico() != null;
    }

    @Override
    public List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback d, RespuestaPreguntaFeedbackDto r) {
        BigDecimal valor = r.getValorNumerico();
        boolean existe = d.niveles().stream().anyMatch(n -> n.getValor().compareTo(valor) == 0);
        if (!existe) {
            throw new IllegalArgumentException("Elige una de las opciones de la escala.");
        }
        BigDecimal min = d.niveles().stream().map(n -> n.getValor()).min(BigDecimal::compareTo).orElseThrow();
        BigDecimal max = d.niveles().stream().map(n -> n.getValor()).max(BigDecimal::compareTo).orElseThrow();
        return List.of(FeedbackAnswer.builder()
                .questionId(d.pregunta().getId())
                .valorNumerico(valor)
                .valorNormalizado(Normalizacion.normalizar(valor, min, max))
                .build());
    }
}
