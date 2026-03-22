package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad;

import jakarta.validation.constraints.NotNull;
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
public class ActualizarActividadDto extends CrearActividadDto {

    @NotNull(message = "El ID de la actividad es obligatorio")
    private Long id;

    private String estado; // BORRADOR, ACTIVA, PAUSADA, etc.
}
