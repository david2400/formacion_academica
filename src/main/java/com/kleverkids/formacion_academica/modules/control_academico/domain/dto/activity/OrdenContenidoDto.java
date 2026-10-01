package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenContenidoDto {

    @NotNull
    private Long contentItemId;

    @NotNull
    private Integer orden;
}
