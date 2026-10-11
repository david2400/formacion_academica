package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_results;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ComentarioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.FiltroResultadosFeedbackDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultarComentariosFeedbackUseCase {
    Page<ComentarioFeedbackDto> comentarios(FiltroResultadosFeedbackDto filtro, Pageable pageable);
}
