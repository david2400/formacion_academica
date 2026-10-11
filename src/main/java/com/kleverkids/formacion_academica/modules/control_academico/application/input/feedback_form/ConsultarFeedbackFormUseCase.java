package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;

public interface ConsultarFeedbackFormUseCase {
    FeedbackFormDetalleDto consultar(Long formId);
}
