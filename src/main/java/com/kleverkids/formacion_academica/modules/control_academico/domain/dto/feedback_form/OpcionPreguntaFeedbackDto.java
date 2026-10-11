package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Opción de una pregunta de selección. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpcionPreguntaFeedbackDto {

    @NotBlank(message = "La opción necesita un texto")
    @Size(max = 200)
    private String etiqueta;

    /** Opcional: si todas las opciones tienen valor, la pregunta entra en promedios. */
    private BigDecimal valor;
}
