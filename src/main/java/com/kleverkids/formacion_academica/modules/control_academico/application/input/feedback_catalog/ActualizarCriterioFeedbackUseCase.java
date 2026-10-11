package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarCriterioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;

public interface ActualizarCriterioFeedbackUseCase {
    FeedbackCriterion actualizarCriterio(Long criterionId, GuardarCriterioFeedbackDto dto);
}
