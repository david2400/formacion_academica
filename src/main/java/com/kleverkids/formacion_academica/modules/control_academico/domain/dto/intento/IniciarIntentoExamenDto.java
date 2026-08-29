package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
public class IniciarIntentoExamenDto {
    private Long examenId;
    private Long estudianteId;

    public IniciarIntentoExamenDto(Long examenId, Long estudianteId) {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        this.examenId = examenId;
        this.estudianteId = estudianteId;
    }

}
