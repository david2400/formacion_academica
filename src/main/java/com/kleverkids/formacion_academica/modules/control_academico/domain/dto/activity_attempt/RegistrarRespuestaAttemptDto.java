package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.SolicitudValidacionRespuesta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Reutiliza {@code SolicitudValidacionRespuesta} (ya existente en el módulo
 * de preguntas) en vez de duplicar sus 8 campos — el estudiante responde con
 * exactamente la misma forma que {@code ServicioValidacionRespuesta} ya sabe
 * interpretar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrarRespuestaAttemptDto {

    private Long attemptId;
    private Long contentItemId;
    private SolicitudValidacionRespuesta respuesta;
}
