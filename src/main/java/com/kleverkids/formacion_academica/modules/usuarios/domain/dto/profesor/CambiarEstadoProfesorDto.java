package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoProfesorDto {
    @NotNull(message = "Indica si el profesor queda activo")
    private Boolean activo;
}
