package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEstudianteDto extends CrearEstudianteDto {
    @NotNull(message = "El identificador del registro es obligatorio")
    private Long id;
}
