package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Registro de QUIÉN respondió (aplicación, clase): sirve para impedir
 * respuestas duplicadas y para calcular participación, también en formularios
 * anónimos.
 *
 * <p>Deliberadamente sin id autoincremental ni hora: si tuviera alguno de los
 * dos, ordenar participaciones y respuestas por inserción permitiría
 * emparejarlas y revelar la identidad en un formulario anónimo.
 * {@link #numero} es 1 salvo que el formulario permita respuestas múltiples.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackParticipation {

    private Long assignmentId;
    private Long claseId;
    private Long estudianteId;
    private Integer numero;
    private LocalDate fecha;
}
