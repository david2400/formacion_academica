package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta/edición de una sección. El orden lo asigna el servidor (al final). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuardarSeccionFeedbackDto {

    @NotBlank(message = "El título de la sección es obligatorio")
    @Size(max = 200)
    private String titulo;

    @Size(max = 1000)
    private String descripcion;
}
