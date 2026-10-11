package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Nivel de una escala (valor + etiqueta). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelEscalaFeedbackDto {

    @NotNull(message = "Cada nivel necesita un valor")
    private BigDecimal valor;

    @NotBlank(message = "Cada nivel necesita una etiqueta")
    @Size(max = 100)
    private String etiqueta;
}
