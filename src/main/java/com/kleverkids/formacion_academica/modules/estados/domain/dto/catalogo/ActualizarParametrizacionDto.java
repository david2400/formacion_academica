package com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Ajusta si el estado es inicial, final o su orden dentro del contexto. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarParametrizacionDto {

    private Boolean esInicial;

    private Boolean esFinal;

    private Integer orden;
}
