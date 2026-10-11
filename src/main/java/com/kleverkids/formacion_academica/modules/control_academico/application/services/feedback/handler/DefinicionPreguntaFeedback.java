package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;

import java.util.List;

/** Pregunta con todo lo necesario para validarla o validar sus respuestas. */
public record DefinicionPreguntaFeedback(FeedbackQuestion pregunta,
                                         List<FeedbackQuestionOption> opciones,
                                         List<FeedbackScaleLevel> niveles) {

    public DefinicionPreguntaFeedback {
        opciones = opciones == null ? List.of() : opciones;
        niveles = niveles == null ? List.of() : niveles;
    }
}
