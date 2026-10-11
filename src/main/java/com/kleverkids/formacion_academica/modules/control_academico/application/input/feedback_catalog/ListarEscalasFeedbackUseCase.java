package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackScaleDetalleDto;

import java.util.List;

public interface ListarEscalasFeedbackUseCase {
    List<FeedbackScaleDetalleDto> listarEscalas();
}
