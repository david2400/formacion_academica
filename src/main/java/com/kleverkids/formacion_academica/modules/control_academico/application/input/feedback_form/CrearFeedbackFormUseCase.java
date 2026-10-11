package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.CrearFeedbackFormDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;

public interface CrearFeedbackFormUseCase {
    FeedbackForm crear(CrearFeedbackFormDto dto);
}
