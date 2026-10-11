package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarSeccionFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;

public interface ActualizarSeccionFeedbackUseCase {
    FeedbackSection actualizarSeccion(Long formId, Long sectionId, GuardarSeccionFeedbackDto dto);
}
