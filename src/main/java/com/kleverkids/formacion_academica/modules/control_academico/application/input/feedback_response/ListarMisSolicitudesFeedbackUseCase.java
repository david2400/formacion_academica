package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.SolicitudFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.EstadoSolicitudFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Bandeja del estudiante. El id SIEMPRE sale del token, nunca de la URL. */
public interface ListarMisSolicitudesFeedbackUseCase {
    Page<SolicitudFeedbackDto> listarMisSolicitudes(Long estudianteId, EstadoSolicitudFeedback estado, Pageable pageable);
}
