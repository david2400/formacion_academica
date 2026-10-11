package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Edición de un formulario en BORRADOR. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarFeedbackFormDto {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200)
    private String titulo;

    @Size(max = 1000)
    private String descripcion;

    private boolean anonimo;

    private boolean permiteMultiplesRespuestas;
}
