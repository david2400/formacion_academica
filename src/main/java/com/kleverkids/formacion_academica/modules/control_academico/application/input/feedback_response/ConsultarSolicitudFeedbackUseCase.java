package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.SolicitudFeedbackDetalleDto;

public interface ConsultarSolicitudFeedbackUseCase {
    SolicitudFeedbackDetalleDto consultarSolicitud(Long estudianteId, Long assignmentId, Long claseId);
}
