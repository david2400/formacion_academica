package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Una respuesta enviada a un formulario para una clase.
 *
 * <p><b>Anonimato:</b> si el formulario es anónimo, {@link #estudianteId} es
 * NULL y {@link #enviadoEn} se trunca a la hora. La identidad del
 * estudiante solo vive en {@code FeedbackParticipation}, sin ninguna columna
 * que la una a esta respuesta. Por eso esta clase NO lleva usrCrea/usrMod: el
 * día que AuditInfo registre el usuario real, guardaría al estudiante aquí.
 *
 * <p>No hay estados PENDIENTE/EN_PROGRESO persistidos: en v1 la respuesta se
 * envía completa en un solo paso (el borrador vive en el cliente).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Long id;
    private Long assignmentId;
    private Long formId;
    private Long claseId;

    /** NULL cuando el formulario es anónimo. */
    private Long estudianteId;

    private LocalDateTime enviadoEn;

    @Builder.Default
    private List<FeedbackAnswer> respuestas = new ArrayList<>();
}
