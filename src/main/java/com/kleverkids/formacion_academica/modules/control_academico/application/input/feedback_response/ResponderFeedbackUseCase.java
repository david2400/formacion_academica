package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ConfirmacionRespuestaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ResponderFeedbackDto;

public interface ResponderFeedbackUseCase {
    ConfirmacionRespuestaFeedbackDto responder(Long estudianteId, Long assignmentId, Long claseId, ResponderFeedbackDto dto);
}
