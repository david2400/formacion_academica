package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarAcudienteDto extends CrearAcudienteDto {

    @NotNull(message = "El identificador del registro es obligatorio")
    private Long id;
}
