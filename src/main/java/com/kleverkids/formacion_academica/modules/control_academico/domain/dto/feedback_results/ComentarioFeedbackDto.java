package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comentario abierto. Nunca incluye al estudiante; en formularios anónimos solo
 * se devuelve si la clase alcanzó el umbral de respuestas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioFeedbackDto {

    private Long preguntaId;
    private String enunciado;
    private String texto;
    private Long claseId;
    private String claseNombre;
    private LocalDate fecha;
}
