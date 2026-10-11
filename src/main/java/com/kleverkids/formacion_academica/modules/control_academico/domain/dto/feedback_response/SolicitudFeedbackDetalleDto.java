package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Solicitud + formulario completo para responder. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudFeedbackDetalleDto {

    private SolicitudFeedbackDto solicitud;
    private FeedbackFormDetalleDto formulario;
}
