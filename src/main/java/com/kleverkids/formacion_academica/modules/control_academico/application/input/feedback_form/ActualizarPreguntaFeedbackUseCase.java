package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarPreguntaFeedbackDto;

public interface ActualizarPreguntaFeedbackUseCase {
    FeedbackFormDetalleDto.PreguntaDetalle actualizarPregunta(Long formId, Long preguntaId, GuardarPreguntaFeedbackDto dto);
}
