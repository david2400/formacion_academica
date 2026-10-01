package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReordenarContenidoActivityDto {

    @NotEmpty(message = "Debe enviar al menos un ítem para reordenar")
    private List<OrdenContenidoDto> items;
}
