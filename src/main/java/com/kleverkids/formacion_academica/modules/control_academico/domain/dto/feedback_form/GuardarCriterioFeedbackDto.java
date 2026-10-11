package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta/edición de un criterio de evaluación. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuardarCriterioFeedbackDto {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 60)
    @Pattern(regexp = "[A-Z0-9_]+", message = "El código solo admite mayúsculas, números y guion bajo")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @Builder.Default
    private boolean activo = true;
}
