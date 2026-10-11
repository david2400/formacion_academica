package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackScaleDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarEscalaFeedbackDto;

public interface CrearEscalaFeedbackUseCase {
    FeedbackScaleDetalleDto crearEscala(GuardarEscalaFeedbackDto dto);
}
