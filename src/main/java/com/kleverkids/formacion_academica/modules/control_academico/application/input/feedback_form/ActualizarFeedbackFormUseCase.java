package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.ActualizarFeedbackFormDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;

public interface ActualizarFeedbackFormUseCase {
    FeedbackForm actualizar(Long formId, ActualizarFeedbackFormDto dto);
}
