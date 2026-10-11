package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_results;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.FiltroResultadosFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ResultadoCriterioFeedbackDto;

import java.util.List;

public interface ConsultarResultadosCriteriosFeedbackUseCase {
    List<ResultadoCriterioFeedbackDto> porCriterio(FiltroResultadosFeedbackDto filtro);
}
