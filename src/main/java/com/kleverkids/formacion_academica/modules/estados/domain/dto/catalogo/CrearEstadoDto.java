package com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Alta o edición de un estado del catálogo. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearEstadoDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    /** Color hexadecimal para la UI. Ej: #2563eb */
    private String color;

    private String icono;

    private Integer orden;
}
