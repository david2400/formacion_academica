package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/** Handlers indexados por nombre de bean (= nombre del enum), poblado por Spring. */
@Component
@RequiredArgsConstructor
public class FeedbackQuestionTypeHandlerRegistry {

    private final Map<String, FeedbackQuestionTypeHandler> handlers;

    public FeedbackQuestionTypeHandler obtener(FeedbackQuestionType tipo) {
        FeedbackQuestionTypeHandler handler = handlers.get(tipo.name());
        if (handler == null) {
            throw new IllegalStateException("No existe un manejador para el tipo de pregunta '" + tipo + "'");
        }
        return handler;
    }
}
