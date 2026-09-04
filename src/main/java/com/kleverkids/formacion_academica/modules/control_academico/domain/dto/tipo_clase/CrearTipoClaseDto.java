package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearTipoClaseDto {

    @NotBlank(message = "El nombre del tipo de clase es obligatorio")
    private String nombre;

    private String descripcion;

    /** Color hex para el calendario (ej. #2563eb). Opcional. */
    private String color;
}
