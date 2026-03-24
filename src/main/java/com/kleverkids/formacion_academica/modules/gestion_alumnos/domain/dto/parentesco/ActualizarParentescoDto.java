package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarParentescoDto extends CrearParentescoDto {
    @NotNull(message = "El identificador del registro es obligatorio")
    private Long id;
}
