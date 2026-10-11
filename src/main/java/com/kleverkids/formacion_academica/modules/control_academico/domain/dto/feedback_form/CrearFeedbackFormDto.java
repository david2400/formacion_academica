package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta de un formulario (nace en BORRADOR, versión 1). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearFeedbackFormDto {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200)
    private String titulo;

    @Size(max = 1000)
    private String descripcion;

    /** Identificado (false) o anónimo (true). Fijo una vez publicado. */
    private boolean anonimo;

    private boolean permiteMultiplesRespuestas;
}
