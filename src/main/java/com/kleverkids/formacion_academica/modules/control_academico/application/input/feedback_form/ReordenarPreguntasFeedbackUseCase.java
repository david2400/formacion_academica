package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.ReordenarFeedbackDto;

public interface ReordenarPreguntasFeedbackUseCase {
    void reordenarPreguntas(Long formId, Long sectionId, ReordenarFeedbackDto dto);
}
