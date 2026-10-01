package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearLearningSequenceDto {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;
}
