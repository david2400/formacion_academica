package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Sí = 1, No = 0: el promedio normalizado es directamente el porcentaje de "sí". */
@Component("YES_NO")
public class YesNoQuestionHandler implements FeedbackQuestionTypeHandler {

    @Override
    public void validarDefinicion(DefinicionPreguntaFeedback d) {
        // Sin configuración adicional.
    }

    @Override
    public boolean respondida(RespuestaPreguntaFeedbackDto r) {
        return r != null && r.getValorNumerico() != null;
    }

    @Override
    public List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback d, RespuestaPreguntaFeedbackDto r) {
        BigDecimal valor = r.getValorNumerico();
        if (valor.compareTo(BigDecimal.ZERO) != 0 && valor.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("Responde Sí o No.");
        }
        BigDecimal entero = valor.compareTo(BigDecimal.ONE) == 0 ? BigDecimal.ONE : BigDecimal.ZERO;
        return List.of(FeedbackAnswer.builder()
                .questionId(d.pregunta().getId())
                .valorNumerico(entero)
                .valorNormalizado(entero)
                .build());
    }
}
