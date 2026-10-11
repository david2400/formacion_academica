package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Selección múltiple: una fila de respuesta por opción marcada; solo distribución, sin promedio. */
@Component("MULTIPLE_CHOICE")
public class MultipleChoiceQuestionHandler implements FeedbackQuestionTypeHandler {

    @Override
    public void validarDefinicion(DefinicionPreguntaFeedback d) {
        SingleChoiceQuestionHandler.validarOpciones(d);
    }

    @Override
    public boolean respondida(RespuestaPreguntaFeedbackDto r) {
        return r != null && r.getOpcionesIds() != null && !r.getOpcionesIds().isEmpty();
    }

    @Override
    public List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback d, RespuestaPreguntaFeedbackDto r) {
        Set<Long> validas = new HashSet<>();
        d.opciones().forEach(o -> validas.add(o.getId()));
        Set<Long> elegidas = new LinkedHashSet<>(r.getOpcionesIds());
        if (!validas.containsAll(elegidas)) {
            throw new IllegalArgumentException("Alguna opción elegida no pertenece a esta pregunta.");
        }
        List<FeedbackAnswer> filas = new ArrayList<>();
        for (Long opcionId : elegidas) {
            filas.add(FeedbackAnswer.builder()
                    .questionId(d.pregunta().getId())
                    .optionId(opcionId)
                    .build());
        }
        return filas;
    }
}
