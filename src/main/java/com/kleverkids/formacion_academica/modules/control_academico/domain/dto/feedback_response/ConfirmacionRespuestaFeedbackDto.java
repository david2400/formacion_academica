package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resultado de enviar un feedback. Deliberadamente NO devuelve el id de la
 * respuesta: en un formulario anónimo ese id, guardado por el cliente,
 * bastaría para emparejar al estudiante con su respuesta.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmacionRespuestaFeedbackDto {

    private Long assignmentId;
    private Long claseId;
    private LocalDateTime recibidoEn;
    private boolean anonimo;
}
