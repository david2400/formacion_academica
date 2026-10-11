package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Selección única. Si TODAS las opciones tienen valor numérico, la respuesta
 * entra en promedios (ej. "Nada / Poco / Bastante / Mucho" = 1..4); si ninguna
 * lo tiene es categórica (solo distribución). Mezclar no se permite porque
 * produciría promedios engañosos.
 */
@Component("SINGLE_CHOICE")
public class SingleChoiceQuestionHandler implements FeedbackQuestionTypeHandler {

    @Override
    public void validarDefinicion(DefinicionPreguntaFeedback d) {
        validarOpciones(d);
        long conValor = d.opciones().stream().filter(o -> o.getValor() != null).count();
        if (conValor != 0 && conValor != d.opciones().size()) {
            throw new IllegalArgumentException("Asigna un valor a todas las opciones o a ninguna.");
        }
    }

    static void validarOpciones(DefinicionPreguntaFeedback d) {
        if (d.opciones().size() < 2) {
            throw new IllegalArgumentException("Agrega al menos 2 opciones.");
        }
        Set<String> etiquetas = new HashSet<>();
        for (FeedbackQuestionOption opcion : d.opciones()) {
            if (opcion.getEtiqueta() == null || opcion.getEtiqueta().isBlank()) {
                throw new IllegalArgumentException("Todas las opciones necesitan un texto.");
            }
            if (!etiquetas.add(opcion.getEtiqueta().trim().toLowerCase())) {
                throw new IllegalArgumentException("Hay opciones repetidas: \"" + opcion.getEtiqueta().trim() + "\".");
            }
        }
    }

    @Override
    public boolean respondida(RespuestaPreguntaFeedbackDto r) {
        return r != null && r.getOpcionId() != null;
    }

    @Override
    public List<FeedbackAnswer> convertir(DefinicionPreguntaFeedback d, RespuestaPreguntaFeedbackDto r) {
        FeedbackQuestionOption opcion = d.opciones().stream()
                .filter(o -> o.getId().equals(r.getOpcionId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("La opción elegida no pertenece a esta pregunta."));

        BigDecimal normalizado = null;
        if (opcion.getValor() != null) {
            BigDecimal min = d.opciones().stream().map(FeedbackQuestionOption::getValor).min(BigDecimal::compareTo).orElseThrow();
            BigDecimal max = d.opciones().stream().map(FeedbackQuestionOption::getValor).max(BigDecimal::compareTo).orElseThrow();
            normalizado = Normalizacion.normalizar(opcion.getValor(), min, max);
        }
        return List.of(FeedbackAnswer.builder()
                .questionId(d.pregunta().getId())
                .optionId(opcion.getId())
                .valorNumerico(opcion.getValor())
                .valorNormalizado(normalizado)
                .build());
    }
}
