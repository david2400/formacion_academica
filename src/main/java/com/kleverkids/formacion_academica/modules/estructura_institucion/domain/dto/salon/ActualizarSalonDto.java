package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActualizarSalonDto extends CrearSalonDto {
    @NotNull(message = "El identificador del grado es obligatorio")
    private Long id;
}
