package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Nueva anotación para la bitácora de una clase. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarObservacionClaseDto {

    @NotBlank(message = "La observación es obligatoria")
    private String observacion;
}
