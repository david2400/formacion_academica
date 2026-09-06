package com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Habilita un estado del catálogo dentro de un contexto ya registrado. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabilitarEstadoDto {

    @NotNull(message = "El estado es obligatorio")
    private Long estadoId;

    private Boolean esInicial;

    private Boolean esFinal;

    private Integer orden;

    /** Null o 0 = parametrización global para todas las empresas. */
    private Long idEmpresa;
}
