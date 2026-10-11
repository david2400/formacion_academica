package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta/edición de una escala reutilizable con sus niveles (reemplazo completo). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuardarEscalaFeedbackDto {

    @NotBlank(message = "El nombre de la escala es obligatorio")
    @Size(max = 150)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @Builder.Default
    private boolean activa = true;

    @Valid
    @Size(min = 2, message = "Una escala necesita al menos 2 niveles")
    @Builder.Default
    private List<NivelEscalaFeedbackDto> niveles = new ArrayList<>();
}
