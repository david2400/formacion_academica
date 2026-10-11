package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** Agrupa preguntas dentro de un formulario (orden de presentación al estudiante). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackSection {

    private Long id;
    private Long formId;
    private String titulo;
    private String descripcion;
    private Integer orden;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
