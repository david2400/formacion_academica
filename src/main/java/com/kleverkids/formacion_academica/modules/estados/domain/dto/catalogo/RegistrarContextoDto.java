package com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Registra un contexto. El {@code codigo} lo calcula el servidor a partir del
 * módulo y la entidad; no se envía.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarContextoDto {

    @NotBlank(message = "El módulo es obligatorio")
    private String modulo;

    @NotBlank(message = "La entidad es obligatoria")
    private String entidad;

    private String nombre;

    private String descripcion;
}
