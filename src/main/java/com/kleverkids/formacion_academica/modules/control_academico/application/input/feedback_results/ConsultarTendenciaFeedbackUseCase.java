package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_results;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.FiltroResultadosFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.PuntoTendenciaFeedbackDto;

import java.util.List;

public interface ConsultarTendenciaFeedbackUseCase {
    List<PuntoTendenciaFeedbackDto> tendencia(FiltroResultadosFeedbackDto filtro, FeedbackResultsQueryPort.Granularidad granularidad);
}
