package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.CambiarEstadoFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;

public interface CambiarEstadoFeedbackFormUseCase {
    FeedbackForm cambiarEstado(Long formId, CambiarEstadoFeedbackDto dto);
}
